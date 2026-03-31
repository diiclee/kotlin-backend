package com.example.kotlin_backend.service

import com.example.kotlin_backend.dto.request.CreateProjectRequest
import com.example.kotlin_backend.dto.request.CreateTaskRequest
import com.example.kotlin_backend.dto.request.CreateUserRequest
import com.example.kotlin_backend.dto.request.UpdateTaskRequest
import com.example.kotlin_backend.dto.request.AssignTaskRequest
import com.example.kotlin_backend.entity.Project
import com.example.kotlin_backend.entity.Task
import com.example.kotlin_backend.entity.User
import com.example.kotlin_backend.entity.enums.TaskPriority
import com.example.kotlin_backend.exception.ResourceNotFoundException
import com.example.kotlin_backend.mapper.ProjectMapper
import com.example.kotlin_backend.mapper.TaskMapper
import com.example.kotlin_backend.mapper.UserMapper
import com.example.kotlin_backend.repository.ProjectRepository
import com.example.kotlin_backend.repository.TaskRepository
import com.example.kotlin_backend.repository.UserRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class ServiceTest {

    //Mocks
    @Mock private lateinit var userRepository: UserRepository
    @Mock private lateinit var userMapper: UserMapper
    @InjectMocks private lateinit var userService: UserService

    @Mock private lateinit var projectRepository: ProjectRepository
    @Mock private lateinit var projectMapper: ProjectMapper
    @InjectMocks private lateinit var projectService: ProjectService

    @Mock private lateinit var taskRepository: TaskRepository
    @Mock private lateinit var taskMapper: TaskMapper
    @InjectMocks private lateinit var taskService: TaskService

    // Helpers
    private fun dummyUser() = User(id = 1L, name = "Alice", email = "alice@example.com")
    private fun dummyProject() = Project(id = 1L, title = "Project", owner = dummyUser())
    private fun dummyTask() = Task(id = 1L, title = "Task 1", priority = TaskPriority.HIGH, project = dummyProject())

    //  UC1: Create User
    @Test
    fun `createUser should return user response`() {
        val request = CreateUserRequest("Alice", "alice@example.com")
        val user = dummyUser()
        `when`(userMapper.toEntity(request)).thenReturn(user)
        `when`(userRepository.save(user)).thenReturn(user)
        `when`(userMapper.toResponse(user)).thenCallRealMethod()

        assertThat(userService.createUser(request).name).isEqualTo("Alice")
    }

    // UC2: Get User
    @Test
    fun `getUser should return user response`() {
        val user = dummyUser()
        `when`(userRepository.findById(1L)).thenReturn(Optional.of(user))
        `when`(userMapper.toResponse(user)).thenCallRealMethod()

        assertThat(userService.getUser(1L).name).isEqualTo("Alice")
    }

    @Test
    fun `getUser should throw when not found`() {
        `when`(userRepository.findById(99L)).thenReturn(Optional.empty())

        assertThatThrownBy { userService.getUser(99L) }
            .isInstanceOf(ResourceNotFoundException::class.java)
    }

    // UC3: Create Project
    @Test
    fun `createProject should return project response`() {
        val request = CreateProjectRequest("Project", null, 1L)
        val owner = dummyUser()
        val project = dummyProject()
        `when`(userRepository.findById(1L)).thenReturn(Optional.of(owner))
        `when`(projectMapper.toEntity(request, owner)).thenReturn(project)
        `when`(projectRepository.save(project)).thenReturn(project)
        `when`(projectMapper.toResponse(project)).thenCallRealMethod()

        assertThat(projectService.createProject(request).title).isEqualTo("Project")
    }

    @Test
    fun `createProject should throw when owner not found`() {
        `when`(userRepository.findById(99L)).thenReturn(Optional.empty())

        assertThatThrownBy { projectService.createProject(CreateProjectRequest("Project", null, 99L)) }
            .isInstanceOf(ResourceNotFoundException::class.java)
    }

    // UC4: Get Project
    @Test
    fun `getProject should throw when not found`() {
        `when`(projectRepository.findById(99L)).thenReturn(Optional.empty())

        assertThatThrownBy { projectService.getProject(99L) }
            .isInstanceOf(ResourceNotFoundException::class.java)
    }

    //UC5: List Projects
    @Test
    fun `listProjects should return all projects`() {
        val project = dummyProject()
        `when`(projectRepository.findAll()).thenReturn(listOf(project))
        `when`(projectMapper.toResponse(project)).thenCallRealMethod()

        assertThat(projectService.listProjects()).hasSize(1)
    }

    @Test
    fun `listProjectsByUser should throw when user not found`() {
        `when`(userRepository.findById(99L)).thenReturn(Optional.empty())

        assertThatThrownBy { projectService.listProjectsByUser(99L) }
            .isInstanceOf(ResourceNotFoundException::class.java)
    }

    // UC6: Create Task
    @Test
    fun `createTask should return task response`() {
        val request = CreateTaskRequest("Task 1", null, TaskPriority.HIGH, null, 1L)
        val project = dummyProject()
        val task = dummyTask()
        `when`(projectRepository.findById(1L)).thenReturn(Optional.of(project))
        `when`(taskMapper.toEntity(request, project)).thenReturn(task)
        `when`(taskRepository.save(task)).thenReturn(task)
        `when`(taskMapper.toResponse(task)).thenCallRealMethod()

        assertThat(taskService.createTask(request).title).isEqualTo("Task 1")
    }

    @Test
    fun `createTask should throw when project not found`() {
        `when`(projectRepository.findById(99L)).thenReturn(Optional.empty())

        assertThatThrownBy { taskService.createTask(CreateTaskRequest("Task 1", null, TaskPriority.HIGH, null, 99L)) }
            .isInstanceOf(ResourceNotFoundException::class.java)
    }

    // UC7: Get Task
    @Test
    fun `getTask should throw when not found`() {
        `when`(taskRepository.findById(99L)).thenReturn(Optional.empty())

        assertThatThrownBy { taskService.getTask(99L) }
            .isInstanceOf(ResourceNotFoundException::class.java)
    }

    //UC8: Update Task
    @Test
    fun `updateTask should return updated task response`() {
        val task = dummyTask()
        val request = UpdateTaskRequest("New Title", null, TaskPriority.LOW, null)
        `when`(taskRepository.findById(1L)).thenReturn(Optional.of(task))
        `when`(taskRepository.save(task)).thenReturn(task)
        `when`(taskMapper.toResponse(task)).thenCallRealMethod()

        assertThat(taskService.updateTask(1L, request).title).isEqualTo("New Title")
    }

    @Test
    fun `updateTask should throw when not found`() {
        `when`(taskRepository.findById(99L)).thenReturn(Optional.empty())

        assertThatThrownBy { taskService.updateTask(99L, UpdateTaskRequest("New Title", null, TaskPriority.LOW, null)) }
            .isInstanceOf(ResourceNotFoundException::class.java)
    }

    // UC9: Delete Task
    @Test
    fun `deleteTask should delete successfully`() {
        val task = dummyTask()
        `when`(taskRepository.findById(1L)).thenReturn(Optional.of(task))

        assertThatCode { taskService.deleteTask(1L) }.doesNotThrowAnyException()
        verify(taskRepository).delete(task)
    }

    @Test
    fun `deleteTask should throw when not found`() {
        `when`(taskRepository.findById(99L)).thenReturn(Optional.empty())

        assertThatThrownBy { taskService.deleteTask(99L) }
            .isInstanceOf(ResourceNotFoundException::class.java)
    }

    // UC10: Assign Task
    @Test
    fun `assignTask should return task response`() {
        val task = dummyTask()
        val user = dummyUser()
        val request = AssignTaskRequest(1L)
        `when`(taskRepository.findById(1L)).thenReturn(Optional.of(task))
        `when`(userRepository.findById(1L)).thenReturn(Optional.of(user))
        `when`(taskRepository.save(task)).thenReturn(task)
        `when`(taskMapper.toResponse(task)).thenCallRealMethod()

        assertThat(taskService.assignTask(1L, request).title).isEqualTo("Task 1")
    }

    @Test
    fun `assignTask should throw when task not found`() {
        `when`(taskRepository.findById(99L)).thenReturn(Optional.empty())

        assertThatThrownBy { taskService.assignTask(99L, AssignTaskRequest(1L)) }
            .isInstanceOf(ResourceNotFoundException::class.java)
    }
}