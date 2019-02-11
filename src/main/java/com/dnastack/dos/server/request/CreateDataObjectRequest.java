package com.dnastack.dos.server.request;

import com.dnastack.dos.server.model.DataObject;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateDataObjectRequest {

    @Valid
    @NotNull
    private DataObject data_object;

}