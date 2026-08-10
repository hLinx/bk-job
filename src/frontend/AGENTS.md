# AGENTS.md

本文件面向 AI 编码助手，描述 BK-JOB（蓝鲸智云作业平台）前端项目的编码规范与协作约定。

## 项目概览

- 技术栈：Vue 2.7.8（Options API 与 `<script setup>` 并存）、vue-router 3、vuex 3、vue-i18n 8、webpack 5
- UI 组件库：`bk-magic-vue`（蓝鲸 MagicBox）及 `@blueking/*` 系列组件
- 环境要求：Node >= 24，npm >= 10
- 样式方案：PostCSS（`postcss-nested` 嵌套、`postcss-mixins`、`postcss-advanced-variables`、`postcss-property-lookup`）

## 常用命令

```bash
npm run dev          # 本地 http 开发（需先创建 .env.development，配置 AJAX_URL_PREFIX）
npm run dev:https    # 本地 https 开发
npm run build        # 生产构建
npm run lint:js      # ESLint 检查并自动修复 src 下的 .js/.vue
npm run lint:style   # Stylelint 检查并自动修复 src 下的 .vue/.css
```

## 目录结构

```
src/
├── views/          # 页面模块，每个模块包含 index.vue、routes.js、language/、index/ 或 components/
├── components/     # 全局公共组件（kebab-case 目录，内置组件多以 jb- 前缀）
├── domain/         # 领域层
│   ├── source/     #   HTTP 请求层：class 继承 ModuleBase，导出单例
│   ├── service/    #   服务层：调用 source 并处理响应数据，导出普通对象
│   └── model/      #   数据模型层
├── router/         # 路由入口（聚合各 views 模块的 routes.js）
├── store/          # vuex
├── i18n/           # 全局语言包与 i18n 实例
├── utils/          # 工具函数（含 Request 请求封装）
├── common/         # 公共初始化逻辑（如 bkmagic 注册）
├── css/            # 全局样式
└── main.js         # 入口
```

## 路径别名

导入必须使用 webpack 别名（`jsconfig.json` 已同步配置）：

| 别名 | 指向 |
| --- | --- |
| `@/*` | `src/*` |
| `@views/*` | `src/views/*` |
| `@components/*` | `src/components/*` |
| `@service/*` | `src/domain/service/*` |
| `@model/*` | `src/domain/model/*` |
| `@domain/*` | `src/domain/*` |
| `@utils/*` | `src/utils/*` |
| `@router/*` | `src/router/*` |
| `@store/*` | `src/store/*` |
| `@common/*` | `src/common/*` |
| `@static/*` | `static/*` |
| `@bk-icon/*` | `lib/bk-icon/*` |

## 编码规范

### 版权头（必须）

- 新建 `.js` 文件：文件顶部添加 `/* ... */` 形式的 MIT 版权头
- 新建 `.vue`/`.html` 文件：文件顶部添加 `<!-- ... -->` 形式的版权头
- 文案模板见 `auto-copyright.js`（也可运行 `node auto-copyright.js` 批量补齐）

### JavaScript / import

- 2 空格缩进，单引号，语句末尾加分号
- import 顺序由 `simple-import-sort` 强制（分组自上而下）：第三方包 → `@lib` → `@router` → `@service` → `@model` → `@utils` → `@views` → `@components` → 其他 `@xxx`（如 `@blueking`）→ `@/` → `../` → `./`
- `no-unused-vars` 为 error；禁止使用下划线命名（`__loadAssetsUrl__` 除外）
- 注释使用 JSDoc 风格（`/** @desc ... */`），函数用途用中文说明

### Vue 单文件组件

- 模板中的组件名一律使用 kebab-case（如 `<jb-dialog>`、`<layout-card>`）
- 属性顺序由 `vue/attributes-order` 约束（`v-for` → `v-if` → `ref`/`key`/`slot` → `v-model` → 指令 → 普通属性 → 事件 → 内容），同组内按字母序
- 布尔属性使用简写（`disabled` 而非 `:disabled="true"`）；静态 class 与动态 class 分开写
- `<script>` 块内容整体缩进一级（`vue/script-indent`: baseIndent 1）
- 禁止无意义的 mustaches 与 v-bind（如 `{{ 'text' }}`、`:title="'text'"`）

### 样式（PostCSS）

- 组件样式写 `<style lang='postcss'>`，使用嵌套语法
- CSS 属性书写顺序由 stylelint `order/properties-order` 强制：定位（position/top/z-index）→ 布局（display/float/width/height）→ 盒模型（padding/margin/overflow）→ 字体文本 → 背景边框 → 动画变换等
- 透明度值使用百分比记法（如 `opacity: 50%`）
- 主题色等常用值参考 `src/css/` 下已有变量与写法

### 分层请求写法

- `domain/source/`：class 继承 `ModuleBase`，构造函数中设置 `this.module = '<后端模块名>'`，方法内调用 `Request.get/post`，文件底部 `export default new Xxx()`
- `domain/service/`：导出普通对象，方法调用对应 source，用 `.then(({ data }) => data)` 提取数据；列表等只读数据用 `Object.freeze` 包裹
- 组件不直接访问 source 层，须经 service 层

### 路由

- 每个页面模块在自己的 `routes.js` 中导出路由配置（`path`、`component`、`meta.group`、`meta.title`），由 `src/router` 聚合
- 页面组件使用动态 `import('@views/...')` 懒加载；`meta.title` 用 `I18n.t('...')` 取值

### 国际化（i18n）

- 全局文案放在 `src/i18n/language/{zh,en}.json`
- 模块文案放在本模块 `language/{zh,en}.json` + `language/index.js`（导出 `{ <命名空间>: { 'zh-CN': zhCN, 'en-US': enUS } }`）
- 模块入口 `index.vue` 中调用 `loadLanguage(Language)` 注册
- 模板中使用 `$t('<命名空间>.<中文key>')`，如 `$t('home.作业量')`；新增文案须同时补充 zh 与 en

## 协作约定

- 提交前运行 `npm run lint:js` 与 `npm run lint:style`，确保无 error
- 仅修改与当前任务直接相关的代码，不顺手重构无关模块
- 修改导致无效的 import、废弃变量可直接删除；发现项目原有的死代码仅提醒，不擅自删除
- 全局只读变量（构建期注入）：`NODE_ENV`、`AJAX_URL_PREFIX`、`AJAX_MOCK_PARAM`、`LOCAL_DEV_URL`、`LOCAL_DEV_PORT`、`USER_INFO_URL`
- ESLint 忽略目录：`lib/`、`static/`、`mock/`、`dist/`、`**/bk-icon/`、`**/iconcool.js`，勿在其中新增需 lint 的代码
