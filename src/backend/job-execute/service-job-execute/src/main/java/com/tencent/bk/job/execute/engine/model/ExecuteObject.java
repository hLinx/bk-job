/*
 * Tencent is pleased to support the open source community by making BK-JOB蓝鲸智云作业平台 available.
 *
 * Copyright (C) 2021 THL A29 Limited, a Tencent company.  All rights reserved.
 *
 * BK-JOB蓝鲸智云作业平台 is licensed under the MIT License.
 *
 * License for BK-JOB蓝鲸智云作业平台:
 * --------------------------------------------------------------------
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

package com.tencent.bk.job.execute.engine.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tencent.bk.job.common.annotation.PersistenceObject;
import com.tencent.bk.job.common.constant.ExecuteObjectTypeEnum;
import com.tencent.bk.job.common.model.dto.Container;
import com.tencent.bk.job.common.model.dto.HostDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * 作业执行对象通用模型
 */
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@PersistenceObject
@Slf4j
public class ExecuteObject implements Cloneable {
    /**
     * 执行对象 ID
     */
    private String id;

    /**
     * 执行对象类型
     *
     * @see ExecuteObjectTypeEnum
     */
    private ExecuteObjectTypeEnum type;

    /**
     * 容器
     */
    private Container container;

    /**
     * 主机
     */
    private HostDTO host;

    public ExecuteObject(String id, Container container) {
        this.id = id;
        this.type = ExecuteObjectTypeEnum.CONTAINER;
        this.container = container;
    }

    public ExecuteObject(String id, HostDTO host) {
        this.id = id;
        this.type = ExecuteObjectTypeEnum.HOST;
        this.host = host;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ExecuteObjectTypeEnum fromExecuteObjectTypeValue(int type) {
        return ExecuteObjectTypeEnum.valOf(type);
    }

    @Override
    public ExecuteObject clone() {
        ExecuteObject clone = new ExecuteObject();
        clone.setId(id);
        clone.setType(type);
        if (host != null) {
            clone.setHost(host.clone());
        }
        if (container != null) {
            clone.setContainer(container);
        }
        return clone;
    }
}
