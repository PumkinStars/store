package com.online.store.ApiResponses;


import com.online.store.Models.UserEntity;
import com.online.store.Utility.AdminResponseMessage;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AdminResponse {
    private AdminResponseMessage message;
    private UserEntity userModified;
}
