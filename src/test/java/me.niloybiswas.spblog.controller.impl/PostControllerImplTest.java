package me.niloybiswas.spblog.controller.impl;

import me.niloybiswas.spblog.controller.impl.PostControllerImpl;
import me.niloybiswas.spblog.dto.PostDTO;
import me.niloybiswas.spblog.mapper.PostMapper;
import me.niloybiswas.spblog.mapper.ReferenceMapper;
import me.niloybiswas.spblog.service.PostService;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class PostControllerImplTest {
    //TODO: create the data Test generator class PostBuilder
    private static final String ENDPOINT_URL = "/posts";
    @MockBean
    private ReferenceMapper referenceMapper;
    @InjectMocks
    private PostControllerImpl postController;
    @MockBean
    private PostService postService;
    @MockBean
    private PostMapper postMapper;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.postController).build();
    }

    @Test
    public void getAll() throws Exception {
        Mockito.when(postMapper.asDTOList(ArgumentMatchers.any())).thenReturn(PostBuilder.getListDTO());

        Mockito.when(postService.findAll()).thenReturn(PostBuilder.getListEntities());
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));

    }

    @Test
    public void getById() throws Exception {
        Mockito.when(postMapper.asDTO(ArgumentMatchers.any())).thenReturn(PostBuilder.getDTO());

        Mockito.when(postService.findById(ArgumentMatchers.anyBigInteger())).thenReturn(java.util.Optional.of(PostBuilder.getEntity()));

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
        Mockito.verify(postService, Mockito.times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(postService);
    }

    @Test
    public void save() throws Exception {
        Mockito.when(postMapper.asEntity(ArgumentMatchers.any())).thenReturn(PostBuilder.getEntity());
        Mockito.when(postService.save(ArgumentMatchers.any(Post.class))).thenReturn(PostBuilder.getEntity());

        mockMvc.perform(
                        MockMvcRequestBuilders.post(ENDPOINT_URL)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(CustomUtils.asJsonString(PostBuilder.getDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(postService, Mockito.times(1)).save(ArgumentMatchers.any(Post.class));
        Mockito.verifyNoMoreInteractions(postService);
    }

    @Test
    public void update() throws Exception {
        Mockito.when(postMapper.asEntity(ArgumentMatchers.any())).thenReturn(PostBuilder.getEntity());
        Mockito.when(postService.update(ArgumentMatchers.any(), ArgumentMatchers.anyBigInteger())).thenReturn(PostBuilder.getEntity());

        mockMvc.perform(
                        MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(CustomUtils.asJsonString(PostBuilder.getDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(postService, Mockito.times(1)).update(ArgumentMatchers.any(Post.class), ArgumentMatchers.anyBigInteger());
        Mockito.verifyNoMoreInteractions(postService);
    }

    @Test
    public void delete() throws Exception {
        Mockito.doNothing().when(postService).deleteById(ArgumentMatchers.anyBigInteger());
        mockMvc.perform(
                        MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(postService, Mockito.times(1)).deleteById(Mockito.anyBigInteger());
        Mockito.verifyNoMoreInteractions(postService);
    }