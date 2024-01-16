package com.example.bpp.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

/**
 *  测试Excel文件上传
 * @author xuefei
 */

@Data
@Accessors(chain = true)
@ApiModel(value = "ReportUploadAndDownloadVo", description = "报表模板导入导出服务 业务入参实体")
public class ReportUploadAndDownloadReqVo {

    private String monthlyModelCreateRecordId;

    private MultipartFile file;

}
