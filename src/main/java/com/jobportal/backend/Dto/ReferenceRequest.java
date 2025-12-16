// ReferenceRequest.java
package com.jobportal.backend.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReferenceRequest {

    @NotBlank(message = "ID không được để trống")
    @Size(max = 50, message = "ID tối đa 50 ký tự")
    private String id;

    @NotBlank(message = "Tên không được để trống")
    @Size(max = 100, message = "Tên tối đa 100 ký tự")
    private String name;
}