package ch.tbmelabs.tv.core.authorizationserver.test.security.logging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.AuthenticationLogCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.bruteforce.BruteforceFilterService;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

@Transactional
public class LoginEndpointIntTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  private static final String LOGIN_PROCESSING_URL = "/signin";
  private static final String USERNAME_PARAMETER_NAME = "username";
  private static final String PASSWORD_PARAMETER_NAME = "password";

  private static final String ERROR_FORWARD_URL = "/signin?error";
  private static final String SUCCESS_FORWARD_URL = "/";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AuthenticationLogCRUDRepository authenticationLogRepository;

  @Mock
  private User userFixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(RandomStringUtils.randomAlphabetic(11)).when(userFixture).getUsername();
    doReturn(RandomStringUtils.randomAlphabetic(11)).when(userFixture).getConfirmation();

    authenticationLogRepository.deleteAll();
    BruteforceFilterService.resetFilter();
  }

  @Test
  public void loginProcessingWithInvalidUsernameShouldFail() throws Exception {
    String redirectUrl = mockMvc
        .perform(post(LOGIN_PROCESSING_URL).param(USERNAME_PARAMETER_NAME, "invalid").param(PASSWORD_PARAMETER_NAME,
            userFixture.getConfirmation()))
        .andDo(print()).andExpect(status().is(HttpStatus.FOUND.value())).andReturn().getResponse().getRedirectedUrl();

    assertThat(redirectUrl).isNotNull().isEqualTo(ERROR_FORWARD_URL);
  }

  @Test
  public void loginProcessingWithInvalidPasswordShoulFail() throws Exception {
    String redirectUrl = mockMvc
        .perform(post(LOGIN_PROCESSING_URL).param(USERNAME_PARAMETER_NAME, userFixture.getUsername())
            .param(PASSWORD_PARAMETER_NAME, "invalid"))
        .andDo(print()).andExpect(status().is(HttpStatus.FOUND.value())).andReturn().getResponse().getRedirectedUrl();

    assertThat(redirectUrl).isNotNull().isEqualTo(ERROR_FORWARD_URL);
  }

  @Test
  public void loginProcessingWithValidCredentialsShouldSucceed() throws Exception {
    String redirectUrl = mockMvc
        .perform(post(LOGIN_PROCESSING_URL).param(USERNAME_PARAMETER_NAME, userFixture.getUsername())
            .param(PASSWORD_PARAMETER_NAME, userFixture.getConfirmation()))
        .andDo(print()).andExpect(status().is(HttpStatus.FOUND.value())).andReturn().getResponse().getRedirectedUrl();

    assertThat(redirectUrl).isNotNull().isEqualTo(SUCCESS_FORWARD_URL);
  }
}