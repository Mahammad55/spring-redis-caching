package az.practice.rediscashing.dto;

import lombok.Data;

@Data
public class UserDto {
    private String name;

    private String surname;

    private Integer age;

    private String address;
}
