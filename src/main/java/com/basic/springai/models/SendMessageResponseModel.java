package com.basic.springai.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SendMessageResponseModel {
    String aiOutput;
}
