package com.dnastack.dos.server.request;

import com.dnastack.dos.server.model.DataBundle;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateDataBundleRequest {

    @Valid
    @NotNull
    private DataBundle data_bundle;

}
