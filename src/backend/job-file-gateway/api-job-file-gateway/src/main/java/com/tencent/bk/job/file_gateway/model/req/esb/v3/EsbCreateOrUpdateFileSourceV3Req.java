package com.tencent.bk.job.file_gateway.model.req.esb.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencent.bk.job.common.esb.model.EsbAppScopeReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class EsbCreateOrUpdateFileSourceV3Req extends EsbAppScopeReq {

    @ApiModelProperty(value = "ID,更新文件源的时候需要传入，新建文件源不需要")
    private Integer id;
    /**
     * 文件源Code
     */
    @ApiModelProperty(value = "文件源Code")
    private String code;
    /**
     * 文件源别名
     */
    @ApiModelProperty(value = "文件源名称")
    private String alias;
    /**
     * 文件源类型
     */
    @ApiModelProperty(value = "文件源类型")
    private String type;

    /**
     * 文件源信息Map
     */
    @ApiModelProperty(value = "文件源信息Map")
    @JsonProperty(value = "access_params")
    private Map<String, Object> accessParams = new HashMap<>();
    /**
     * 文件源凭证Id
     */
    @ApiModelProperty(value = "文件源凭证Id")
    @JsonProperty(value = "credential_id")
    private String credentialId;
    /**
     * 文件前缀
     */
    @ApiModelProperty(value = "文件前缀：后台自动生成UUID传${UUID}，自定义字符串直接传")
    @JsonProperty(value = "file_prefix")
    private String filePrefix = "";
}
