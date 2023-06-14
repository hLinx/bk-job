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

package com.tencent.bk.job.common.encrypt.scenario;

import com.tencent.bk.job.common.constant.TaskVariableTypeEnum;
import com.tencent.bk.job.common.encrypt.CryptoScenarioEnum;
import com.tencent.bk.job.common.encrypt.SymmetricCryptoService;
import com.tencent.bk.sdk.crypto.cryptor.consts.CryptorNames;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 密文变量相关加解密服务
 */
@Slf4j
@Service
public class CipherVariableService {

    private final SymmetricCryptoService symmetricCryptoService;

    @Autowired
    public CipherVariableService(SymmetricCryptoService symmetricCryptoService) {
        this.symmetricCryptoService = symmetricCryptoService;
    }

    public String getCipherVariableEncryptAlgorithm(TaskVariableTypeEnum taskVariableTypeEnum) {
        if (!isCipherVariable(taskVariableTypeEnum)) {
            return CryptorNames.NONE;
        }
        return symmetricCryptoService.getAlgorithmByScenario(CryptoScenarioEnum.CIPHER_VARIABLE);
    }

    private boolean isCipherVariable(TaskVariableTypeEnum taskVariableTypeEnum) {
        return TaskVariableTypeEnum.CIPHER == taskVariableTypeEnum;
    }

    public String encryptTaskVariableIfNeeded(TaskVariableTypeEnum taskVariableTypeEnum, String taskVariable) {
        if (!isCipherVariable(taskVariableTypeEnum)) {
            return taskVariable;
        }
        return symmetricCryptoService.encryptToBase64Str(taskVariable, CryptoScenarioEnum.CIPHER_VARIABLE);
    }

    public String decryptTaskVariableIfNeeded(TaskVariableTypeEnum taskVariableTypeEnum,
                                              String encryptedTaskVariable,
                                              String algorithm) {
        if (!isCipherVariable(taskVariableTypeEnum) || StringUtils.isBlank(algorithm)) {
            return encryptedTaskVariable;
        }
        return symmetricCryptoService.decrypt(encryptedTaskVariable, algorithm);
    }

}
