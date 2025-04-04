package com.gj.cloud.base.user.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serial;
import java.io.Serializable;

@Table("users")
@Data
public class UserBean implements Serializable {
    @Serial
    private static final long serialVersionUID = -135977691358754830L;

    @Id
    private Long id;

    private String name;

    private String email;
}
