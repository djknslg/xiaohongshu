package com.zhaitiancai.xiaohongshu.auth.model.vo.veriticationcode;

import com.zhaitiancai.framework.common.validator.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendVerificationCodeReqVO {

    @PhoneNumber
    private String phone;

}