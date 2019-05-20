package rgr.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private Integer result;

    public Integer getResult() {
        return result;
    }
}
