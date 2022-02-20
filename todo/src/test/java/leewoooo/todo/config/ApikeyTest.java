package leewoooo.todo.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApikeyTest {

    @Autowired
    Apikey apikey;

    @Test
    @DisplayName("api key 가져오기")
    void getApikey(){
        //given

        //when
        String apikey = this.apikey.getApikey();

        //then
        Assertions.assertThat(apikey).isEqualTo("123");
    }

}