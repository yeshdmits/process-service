package com.yeshenko.process;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.yeshenko.process.config.AbstractIntegrationTest;
import com.yeshenko.process.domain.enumeration.DocumentStatusEnum;
import com.yeshenko.process.domain.enumeration.ProcessStatusEnum;
import com.yeshenko.process.domain.enumeration.TaskStatusEnum;
import com.yeshenko.process.domain.repository.ProcessDefinitionRepository;
import com.yeshenko.process.domain.util.MapUtil;
import com.yeshenko.process.models.v1.CreateProcess200ResponseDto;
import com.yeshenko.process.models.v1.CreateProcessRequestDto;
import com.yeshenko.process.models.v1.ProcessEntityDto;
import com.yeshenko.process.models.v1.TaskCompleteDto;
import com.yeshenko.process.models.v1.TaskCompleteDto.DecisionEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class DefaultProcessOnboardingIntegrationTest extends AbstractIntegrationTest {

  private static final String CREATE_PROCESS_API_URL = "/api/v1/process";

  @Autowired
  private ProcessDefinitionRepository processDefinitionRepository;

  @Test
  void test_default_process_onboarding_test_primary_flow() throws Exception {
//    given
    var processDefinition = processDefinitionRepository.findByProcessName("defaultProcess")
        .orElseThrow();

    var request = new CreateProcessRequestDto()
        .processDefinitionId(processDefinition.getId());

//  Create Process Instance
    var responseCreate = mockMvc.perform(
        MockMvcRequestBuilders
            .post(CREATE_PROCESS_API_URL)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MapUtil.serializeObjectToString(request))
    ).andReturn().getResponse().getContentAsString();

    var createProcessResponse = MapUtil.serializeObjectFromString(responseCreate, CreateProcess200ResponseDto.class);

//  Get Process Instance
    var responseGetConfigurationBody = mockMvc.perform(
        MockMvcRequestBuilders
            .get(CREATE_PROCESS_API_URL)
            .param("processInstanceId", createProcessResponse.getProcessInstanceId().toString())
    ).andReturn().getResponse().getContentAsString();

    var responseGetConfiguration = MapUtil.serializeObjectFromString(responseGetConfigurationBody,
        ProcessEntityDto.class);

    assertEquals(ProcessStatusEnum.CREATED.getValue(), responseGetConfiguration.getProcessStatus());
    assertEquals("Configuration Task", responseGetConfiguration.getMetadata().getTaskName());
    assertEquals(1, responseGetConfiguration.getTaskList().size());
    assertEquals(TaskStatusEnum.IN_PROGRESS.getValue(), responseGetConfiguration.getTaskList().get(0).getTaskStatus());

//  Complete Configuration Task Process Instance
    var taskConfigurationRequest = new TaskCompleteDto()
        .taskId(responseGetConfiguration.getMetadata().getTaskId())
        .formData("{\"full_name\":\"Random Chel\",\"date_of_birth\":\"1970-02-24\",\"income_source\":\"Business ownership\",\"account_purpose\":\"Personal savings\",\"transaction_frequency\":\"Monthly\",\"average_balance\":\">$10,000\",\"additional_services\":\"Investment advisory\",\"account_management_preference\":\"Mobile app\",\"specific_requirements\":\"Access to ATMs\",\"banking_familiarity\":\"Intermediate\"}")
        .decision(DecisionEnum.COMPLETED);
    mockMvc.perform(
        MockMvcRequestBuilders
            .put(CREATE_PROCESS_API_URL)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MapUtil.serializeObjectToString(taskConfigurationRequest))
    ).andExpect(MockMvcResultMatchers.status().isOk());

//  Get Process Instance
    var responseGetDistributionBody = mockMvc.perform(
        MockMvcRequestBuilders
            .get(CREATE_PROCESS_API_URL)
            .param("processInstanceId", createProcessResponse.getProcessInstanceId().toString())
    ).andReturn().getResponse().getContentAsString();

    var responseGetDistribution = MapUtil.serializeObjectFromString(responseGetDistributionBody, ProcessEntityDto.class);

    assertEquals(ProcessStatusEnum.IN_PROGRESS.getValue(), responseGetDistribution.getProcessStatus());
    assertEquals("Distribution Task", responseGetDistribution.getMetadata().getTaskName());
    assertEquals(2, responseGetDistribution.getTaskList().size());
    assertEquals(1, responseGetDistribution.getDocumentList().size());
    assertEquals(TaskStatusEnum.IN_PROGRESS.getValue(),
        responseGetDistribution.getTaskList().get(1).getTaskStatus());
    assertEquals(DocumentStatusEnum.CREATED.getValue(),
        responseGetDistribution.getDocumentList().get(0).getDocumentStatus());


//  Complete Distribution Task Process Instance
    var taskCompleteDistributionRequest = new TaskCompleteDto()
        .taskId(responseGetDistribution.getMetadata().getTaskId())
        .decision(DecisionEnum.COMPLETED);
    mockMvc.perform(
        MockMvcRequestBuilders
            .put(CREATE_PROCESS_API_URL)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MapUtil.serializeObjectToString(taskCompleteDistributionRequest))
    ).andExpect(MockMvcResultMatchers.status().isOk());

//  Get Process Instance
    var responseGetValidationBody = mockMvc.perform(
        MockMvcRequestBuilders
            .get(CREATE_PROCESS_API_URL)
            .param("processInstanceId", createProcessResponse.getProcessInstanceId().toString())
    ).andReturn().getResponse().getContentAsString();
    var responseGetValidation = MapUtil.serializeObjectFromString(responseGetValidationBody, ProcessEntityDto.class);

    assertEquals(ProcessStatusEnum.IN_PROGRESS.getValue(), responseGetValidation.getProcessStatus());
    assertEquals("Validation Task", responseGetValidation.getMetadata().getTaskName());
    assertEquals(3, responseGetValidation.getTaskList().size());
    assertEquals(1, responseGetValidation.getDocumentList().size());
    assertEquals(TaskStatusEnum.IN_PROGRESS.getValue(),
        responseGetValidation.getTaskList().get(2).getTaskStatus());
    assertEquals(DocumentStatusEnum.SENT.getValue().toLowerCase(),
        responseGetValidation.getDocumentList().get(0).getDocumentStatus());

//  Complete Validation Task Process Instance
    var taskCompleteValidationRequest = new TaskCompleteDto()
        .taskId(responseGetValidation.getMetadata().getTaskId())
        .formData("{\"decision\": \"Approved\"}")
        .decision(DecisionEnum.COMPLETED);
    mockMvc.perform(
        MockMvcRequestBuilders
            .put(CREATE_PROCESS_API_URL)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MapUtil.serializeObjectToString(taskCompleteValidationRequest))
    ).andExpect(MockMvcResultMatchers.status().isOk());

    //  Get Process Instance
    var responseGetActiveBody = mockMvc.perform(
        MockMvcRequestBuilders
            .get(CREATE_PROCESS_API_URL)
            .param("processInstanceId", createProcessResponse.getProcessInstanceId().toString())
    ).andReturn().getResponse().getContentAsString();

    var response = MapUtil.serializeObjectFromString(responseGetActiveBody, ProcessEntityDto.class);


    assertNotNull(response);

    assertEquals(ProcessStatusEnum.ACTIVE.getValue(), response.getProcessStatus());
    assertNull(response.getMetadata());
    assertEquals(3, response.getTaskList().size());
    assertEquals(1, response.getDocumentList().size());
    assertEquals(DocumentStatusEnum.COMPLETED.getValue().toLowerCase(),
        response.getDocumentList().get(0).getDocumentStatus());

  }
}
