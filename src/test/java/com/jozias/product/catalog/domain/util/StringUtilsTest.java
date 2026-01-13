package com.jozias.product.catalog.domain.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.jozias.product.catalog.domain.util.StringUtils;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("StringUtils")
class StringUtilsTest {

    @Nested
    @DisplayName("isAValidString without minLength")
    class IsAValidStringWithoutMinLength {

        @Test
        @DisplayName("given null string when isAValidString then should return false")
        void givenNullString_whenIsAValidString_thenShouldReturnFalse() {
            // given
            String str = null;

            // when
            boolean result = StringUtils.isAValidString(str);

            // then
            assertThat(result).isFalse();
        }

        @ParameterizedTest
        @ValueSource(strings = { "", "   ", "\t", "\n" })
        @DisplayName("given empty or blank string when isAValidString then should return false")
        void givenEmptyOrBlankString_whenIsAValidString_thenShouldReturnFalse(String str) {
            // when
            boolean result = StringUtils.isAValidString(str);

            // then
            assertThat(result).isFalse();
        }

        @ParameterizedTest
        @ValueSource(strings = { "a", "ab", "abc", "hello world" })
        @DisplayName("given valid string when isAValidString then should return true")
        void givenValidString_whenIsAValidString_thenShouldReturnTrue(String str) {
            // when
            boolean result = StringUtils.isAValidString(str);

            // then
            assertThat(result).isTrue();
        }
    }

    @Nested
    @DisplayName("isAValidString with minLength")
    class IsAValidStringWithMinLength {

        @Test
        @DisplayName("given null string when isAValidString with minLength then should return false")
        void givenNullString_whenIsAValidStringWithMinLength_thenShouldReturnFalse() {
            // given
            String str = null;

            // when
            boolean result = StringUtils.isAValidString(str, 3);

            // then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("given string with exact minLength when isAValidString then should return true")
        void givenStringWithExactMinLength_whenIsAValidString_thenShouldReturnTrue() {
            // given
            String str = "abc";

            // when
            boolean result = StringUtils.isAValidString(str, 3);

            // then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("given string below minLength when isAValidString then should return false")
        void givenStringBelowMinLength_whenIsAValidString_thenShouldReturnFalse() {
            // given
            String str = "ab";

            // when
            boolean result = StringUtils.isAValidString(str, 3);

            // then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("given string with spaces when isAValidString then should ignore spaces in length calculation")
        void givenStringWithSpaces_whenIsAValidString_thenShouldIgnoreSpacesInLengthCalculation() {
            // given
            String strWithSpaces = "a b c"; // 3 characters without spaces

            // when
            boolean result = StringUtils.isAValidString(strWithSpaces, 3);

            // then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("given string with only spaces when isAValidString with minLength then should return false")
        void givenStringWithOnlySpaces_whenIsAValidStringWithMinLength_thenShouldReturnFalse() {
            // given
            String str = "   ";

            // when
            boolean result = StringUtils.isAValidString(str, 1);

            // then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("given string with spaces not meeting minLength when isAValidString then should return false")
        void givenStringWithSpacesNotMeetingMinLength_whenIsAValidString_thenShouldReturnFalse() {
            // given
            String str = "a b"; // only 2 characters without spaces

            // when
            boolean result = StringUtils.isAValidString(str, 3);

            // then
            assertThat(result).isFalse();
        }
    }
}
