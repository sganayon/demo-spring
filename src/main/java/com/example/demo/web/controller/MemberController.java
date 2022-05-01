package com.example.demo.web.controller;

import com.example.demo.dto.MemberDto;
import com.example.demo.dto.MemberPostDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        @RequestParam(value = "pageSize", required = false) Integer pageSize,
        @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
        @RequestParam(value = "sortedElement", required = false) String sortedElement,
        @RequestParam(value = "sortedAsc", required = false) Boolean sortedAsc,
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
    public ResponseEntity<MemberDto> getMember(@PathVariable long id){
        MemberDto memberDto = memberFacade.getOneById(id);
        if(memberDto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(memberDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Delete one member")
    public void deleteMember(@PathVariable long id){
        memberFacade.deleteOneById(id);
    }

    // @Valid permet au Test de tester la validation, mais c'est appliqué par défaut lors du run ==> surement un problème de configuration du test
    @PostMapping
    @Operation(description = "Add one member")
    public long addMember(@Valid @RequestBody MemberPostDto memberDto){
        return memberFacade.save(memberDto);
    }

    @PutMapping("/{id}")
    @Operation(description = "Update one member")
    public MemberDto updateMember(@RequestBody MemberPostDto memberDto, @PathVariable long id){
        return memberFacade.update(memberDto, id);
    }
}
