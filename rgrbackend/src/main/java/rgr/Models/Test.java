package rgr.Models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(length = 60, unique = true)
    private String name;

    @NotNull
    @Column(length = 200)
    private String description;

    @OneToMany
    private List<Question> questions;

    @ManyToMany
    private List<User> accessedUsers;

    @OneToMany
    private List<Result> results;

    @ManyToOne
    private User creator;

    public Integer getCompleteCount() {
        return (results!=null) ? results.size() : 0;
    }

    public Integer getAverage() {
        if (results == null) {
            return 0;
        }
        int resultSumm = 0;
        for (Result result : results) {
            resultSumm += result.getResult();
        }
        return (Integer)(resultSumm / results.size());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setAccessedUsers(List<User> accessedUsers) {
        this.accessedUsers = accessedUsers;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<User> getAccessedUsers() {
        return accessedUsers;
    }

    public List<Result> getResults() {
        return results;
    }
}
