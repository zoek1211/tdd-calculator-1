package com.back;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// tdd 3단계 싸이클 Red, green, blue
// red 실패하는 테스트 작성
// green 꼼수를 써서 테스트 통과시킴
// blue 리팩토링
public class CalcTest {
    @Test
    @DisplayName("1 + 1 == 2")
    void t1() {
        assertThat(Calc.run("1 + 1")).isEqualTo(2);
    }
}