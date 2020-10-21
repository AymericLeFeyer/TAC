package fr.leftac.listify.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {
    public String access_token;
    public String token_type;
    public int expires_in;
    public String scope;
}
