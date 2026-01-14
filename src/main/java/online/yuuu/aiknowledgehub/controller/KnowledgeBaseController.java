package online.yuuu.aiknowledgehub.controller;

import online.yuuu.aiknowledgehub.common.Result;
import online.yuuu.aiknowledgehub.dto.KnowledgeBaseCreateRequest;
import online.yuuu.aiknowledgehub.dto.KnowledgeBaseUpdateRequest;
import online.yuuu.aiknowledgehub.entity.KnowledgeBase;
import online.yuuu.aiknowledgehub.service.IKnowledgeBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/knowledge-bases")
public class KnowledgeBaseController {

    @Autowired
    private IKnowledgeBaseService knowledgeBaseService;

    @GetMapping
    public Result<List<KnowledgeBase>> getAllKnowledgeBases() {
        return knowledgeBaseService.getAllKnowledgeBases();
    }

    @GetMapping("/{id}")
    public Result<KnowledgeBase> getKnowledgeBase(@PathVariable Integer id) {
        return knowledgeBaseService.getKnowledgeBaseById(id);
    }

    @PostMapping
    public Result<KnowledgeBase> createKnowledgeBase(@RequestBody KnowledgeBaseCreateRequest request, 
                                                    @RequestParam Integer userId) {
        return knowledgeBaseService.createKnowledgeBase(request, userId);
    }

    @PutMapping("/{id}")
    public Result<KnowledgeBase> updateKnowledgeBase(@PathVariable Integer id, 
                                                    @RequestBody KnowledgeBaseUpdateRequest request,
                                                    @RequestParam Integer userId) {
        request.setId(id);
        return knowledgeBaseService.updateKnowledgeBase(request, userId);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteKnowledgeBase(@PathVariable Integer id, @RequestParam Integer userId) {
        return knowledgeBaseService.deleteKnowledgeBase(id, userId);
    }
}