package leewoooo.todo.ci;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GithubActionsTest {

    @Test
    @DisplayName("Github Actions가 잘 동작하는지")
    void githubActionTest(){
        //given
        int a = 1;
        int b = 2;

        //when
        int result = a + b;

        //then
        assertThat(result).isEqualTo(3);
    }
}
