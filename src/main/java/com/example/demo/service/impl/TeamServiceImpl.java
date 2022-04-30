package com.example.demo.service.impl;

import com.example.demo.dto.DefaultPagination;
import com.example.demo.entity.Member;
import com.example.demo.entity.Team;
import com.example.demo.repository.CrudRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.TeamRepository;
import com.example.demo.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeamServiceImpl extends CrudBaseServiceImpl<Team, String> implements TeamService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    /**
     * La relation 'team' étant porté par les 'member' il faut un peut plus de logique dans ce service
     * @param team la team à sauvegarder avec ses membres
     * @return la team pleinement sauvegardé
     */
    @Override
    public Team save(Team team) {
        Set<Member> members = team.getMembers();
        // On sauvegarde la team (si jamais elle n'existe pas on ne pourra pas save les membres)
        Team savedTeam = super.save(team);
        // On affecte les membres à la team et on les sauvegardes
        members.forEach(member -> member.setTeam(savedTeam));
        members = new HashSet<>(memberRepository.saveAll(members));
        // On affecte les membres à la team et on la retourne (évite un find... pour avoir la liste des membres à jour)
        savedTeam.setMembers(members);
        return savedTeam;
    }

    /**
     * Supprime une team sans supprimer les membres qui la compose
     * @param id de la team à supprimer
     */
    @Override
    public void deleteOneById(String id) {
        Team team = findOneById(id);
        if(team == null){
            return;
        }

        Set<Member> members = team.getMembers();
        members.forEach(member -> member.setTeam(null));
        memberRepository.saveAll(members);
        super.deleteOneById(id);
    }

    @Override
    protected CrudRepository<Team, String> getRepository() {
        return teamRepository;
    }

    @Override
    protected DefaultPagination getDefaultPagination() {
        return new DefaultPagination().withSortElement("name");
    }

    @Override
    public Team update(Team team, String id) {
        // On retire la team des membres qui n'en font plus partie
        removeMemberFromTeam(team.getMembers(), id);
        // Puis on sauvegarde la team avec les nouveaux membres
        Team savedTeam = save(team);
        // Le nom de team à changé : Il faut supprimer l'ancienne team
        if(!team.getName().equals(id)){
            teamRepository.deleteById(id);
        }
        return savedTeam;
    }

    /**
     * Retire l'association entre un membre et une team
     * @param actualTeamMembers Les membres actuels de la team
     * @param teamName l'(ancient) nom de la team pour retrouver les membres encore affectés
     */
    private void removeMemberFromTeam(Set<Member> actualTeamMembers, String teamName){
        List<Member> members = memberRepository.findAllMembersByTeamName(teamName);
        List<Member> removedMember = members.stream()
                .filter(member -> !actualTeamMembers.contains(member))
                .map(this::removeFromTeam)
                .collect(Collectors.toList());
        memberRepository.saveAll(removedMember);
    }

    private Member removeFromTeam(Member member){
        member.setTeam(null);
        return member;
    }
}
