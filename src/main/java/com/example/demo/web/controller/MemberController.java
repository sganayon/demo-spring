package com.example.demo.web.controller;

import com.example.demo.dto.MemberDto;
import com.example.demo.entity.Member;
import com.example.demo.facade.MemberFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Tag(name = "MemberController", description = "API that handle CRUD operations on members")
public class MemberController {
    private final MemberFacade memberFacade;

    @GetMapping
    @Operation(description = "Get all members after applying the filters")
    public List<MemberDto> getMembers(
        @RequestParam("pageSize") Integer pageSize,
        @RequestParam("pageNumber") Integer pageNumber,
        @RequestParam("sortedElement") String sortedElement,
        @RequestParam("sortedAsc") Boolean sortedAsc,
        @Join(path="team", alias="team")
        @And({
              @Spec(path = "id", params = "id", spec = Equal.class),
              @Spec(path = "name", params = "label", spec = LikeIgnoreCase.class),
              @Spec(path = "surname", params = "sector", spec = LikeIgnoreCase.class),
              @Spec(path = "status", params = "status", spec = Equal.class),
              @Spec(path = "team.id", params = "teamId", spec = Equal.class),
              @Spec(path = "team.name", params = "teamName", spec = LikeIgnoreCase.class)
        }) Specification<Member> specification){
        return memberFacade.getMembers(pageNumber, pageSize, sortedElement, sortedAsc, specification);
    }

    @GetMapping("/{id}")
    @Operation(description = "Get one member")
    public MemberDto getMember(@PathVariable long id){
        return memberFacade.getOneById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete one member")
    public void deleteMember(@PathVariable long id){
        memberFacade.deleteOneById(id);
    }

    @PostMapping
    @Operation(description = "Add one member")
    public void addMember(@RequestBody MemberDto memberDto){
        memberFacade.save(memberDto);
    }
}
