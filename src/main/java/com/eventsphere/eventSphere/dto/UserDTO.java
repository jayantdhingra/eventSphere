package com.eventsphere.eventSphere.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotEmpty
    private String userName;
    private String email;
    private boolean sentimentAnalysis;
    @NotEmpty
    private String password;
    @NotEmpty
    private List<String> role;
}
