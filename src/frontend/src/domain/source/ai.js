/*
 * Tencent is pleased to support the open source community by making BK-JOB蓝鲸智云作业平台 available.
 *
 * Copyright (C) 2021 THL A29 Limited, a Tencent company.  All rights reserved.
 *
 * BK-JOB蓝鲸智云作业平台 is licensed under the MIT License.
 *
 * License for BK-JOB蓝鲸智云作业平台:
 *
 * ---------------------------------------------------
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
*/

import Request from '@utils/request';

import ModuleBase from './module-base';

class Ai extends ModuleBase {
  constructor() {
    super();
    this.module = '/job-analysis/web/ai/';
  }

  getAnalyzeError(params, payload = {}) {
    return Request.post(`${this.path}/analyzeError`, {
      params,
      payload,
    });
  }

  getCheckScript(params, payload = {}) {
    return Request.post(`${this.path}/checkScript`, {
      params,
      payload,
    });
  }

  getConfig(params, payload = {}) {
    return Request.get(`${this.path}/config`, {
      params,
      payload,
    });
  }

  getGeneraChat(params, payload = {}) {
    return Request.post(`${this.path}/general/chat`, {
      params,
      payload,
    });
  }

  getLatestChatHistoryList(params) {
    return Request.get(`${this.path}/latestChatHistoryList`, {
      params,
    });
  }

  deleteChatHistory() {
    return Request.delete(`${this.path}/clearChatHistory`);
  }

  getChatStream(params) {
    return Request.get(`${this.path}/chatStream`, {
      params,
    });
  }
  terminateChat(params) {
    return Request.put(`${this.path}/terminateChat`, {
      params,
    });
  }
}

export default new Ai();
