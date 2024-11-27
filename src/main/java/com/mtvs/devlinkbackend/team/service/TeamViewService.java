package com.mtvs.devlinkbackend.team.service;

import com.mtvs.devlinkbackend.member.command.model.entity.Member;
import com.mtvs.devlinkbackend.member.command.repository.MemberRepository;
import com.mtvs.devlinkbackend.project.repository.ProjectViewRepository;
import com.mtvs.devlinkbackend.support.repository.SupportRepository;
import com.mtvs.devlinkbackend.team.dto.response.TeamListResponseDTO;
import com.mtvs.devlinkbackend.team.dto.response.TeamSingleReponseDTO;
import com.mtvs.devlinkbackend.team.dto.response.support.*;
import com.mtvs.devlinkbackend.team.entity.Team;
import com.mtvs.devlinkbackend.team.repository.TeamViewRepository;
import com.mtvs.devlinkbackend.user.command.model.entity.Dev;
import com.mtvs.devlinkbackend.user.command.model.entity.SkillCategoryInfo;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.query.repository.DevViewRepository;
import com.mtvs.devlinkbackend.user.query.repository.SkillCategoryInfoViewRepository;
import com.mtvs.devlinkbackend.user.query.repository.UserViewRepository;
import com.mtvs.devlinkbackend.user.query.service.UserViewService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeamViewService {
    private final TeamViewRepository teamViewRepository;
    private final UserViewRepository userViewRepository;
    private final ProjectViewRepository projectViewRepository;
    private final DevViewRepository devViewRepository;
    private final UserViewService userViewService;
    private final MemberRepository memberRepository;
    private final SupportRepository supportRepository;
    private final SkillCategoryInfoViewRepository skillCategoryInfoViewRepository;

    public TeamViewService(TeamViewRepository teamViewRepository, UserViewRepository userViewRepository, ProjectViewRepository projectViewRepository, DevViewRepository devViewRepository, UserViewService userViewService, MemberRepository memberRepository, SupportRepository supportRepository, SkillCategoryInfoViewRepository skillCategoryInfoViewRepository) {
        this.teamViewRepository = teamViewRepository;
        this.userViewRepository = userViewRepository;
        this.projectViewRepository = projectViewRepository;
        this.devViewRepository = devViewRepository;
        this.userViewService = userViewService;
        this.memberRepository = memberRepository;
        this.supportRepository = supportRepository;
        this.skillCategoryInfoViewRepository = skillCategoryInfoViewRepository;
    }

    public TeamSingleReponseDTO findTeamByTeamId(Long teamId) {
        TeamSingleReponseDTO team  = new TeamSingleReponseDTO(teamViewRepository.findById(teamId).orElse(null));
        for (Long memberId : team.getData().getTeamMemberList()) {
            System.out.println("Member ID type: " + memberId.getClass().getName());
        }
        return team;
    }

    public TeamListResponseDTO findTeamsByLeaderAccountId(String accountId) {
        User user = userViewService.findUserByEpicAccountId(accountId);
        return new TeamListResponseDTO(teamViewRepository.findTeamsByLeaderUserId(user.getUserId()));
    }

    public TeamListResponseDTO findByMemberIdInTeam(Long memberUserId) {
        return new TeamListResponseDTO(teamViewRepository.findByMemberIdInTeam(memberUserId));
    }

    public TeamListResponseDTO findByAccountIdInTeam(String accountId) {
        User foundUser = userViewRepository.findUserByEpicAccountId(accountId);
        return new TeamListResponseDTO(teamViewRepository.findByMemberIdInTeam(foundUser.getUserId()));
    }

    public TeamSupportListResponseDTO getTeamList(Long userId, Long projectId) {
        if(projectViewRepository.findUserIdByProjectId(projectId).equals(userId)){
            List<Long> teamIds = supportRepository.findTeamIdByProjectIdUnConfirmed(projectId);

            TeamSupportListResponseDTO teamSupportListResponseDTO = new TeamSupportListResponseDTO();

            List<TeamSupportResponseDTO> teamSupportResponseDTOS = new ArrayList<>();
            for(Long teamId : teamIds){
                TeamSupportResponseDTO teamSupportResponseDTO = new TeamSupportResponseDTO();
                List<RadarPointDTO> radarPointDTOS = new ArrayList<>();
                Long leaderId = teamViewRepository.findLeaderUserIdByTeamId(teamId);
                User user = userViewRepository.findById(leaderId).orElse(null);
                if(user != null){
                    teamSupportResponseDTO.setExperienceValue(user.getExperienceValue());
                    teamSupportResponseDTO.setTeamId(teamId);
                    teamSupportResponseDTO.setNickname(user.getNickname());
                    Dev dev = devViewRepository.findDevByUserId(leaderId);
                    if(dev != null){
                        teamSupportResponseDTO.setPortfolioList(dev.getPortfolioUrlList());
                        List<SkillCategoryInfo> skillCategoryInfos = skillCategoryInfoViewRepository.findByDevId(dev.getDevId());
                        for(SkillCategoryInfo skillCategoryInfo : skillCategoryInfos){
                            RadarPointDTO radarPointDTO = new RadarPointDTO();
                            radarPointDTO.setCategoryName(skillCategoryInfo.getCategoryName());
                            radarPointDTO.setPointAvg(skillCategoryInfo.getPointAvg());
                            radarPointDTOS.add(radarPointDTO);
                        }
                    }
                    else {
                        throw new IllegalArgumentException("dev 정보 없음");
                    }
                }
                else {
                    throw new IllegalArgumentException("user 정보 없음");
                }
                Team team = teamViewRepository.findById(teamId).orElse(null);
                if(team != null){
                    teamSupportResponseDTO.setTeamIntroduction(team.getTeamIntroduction());
                    List<Long> memberIds = team.getTeamMemberList();
                    List<TeamMemberDTO> teamMemberDTOS = new ArrayList<>();
                    for(Long memberId : memberIds){
                        Member member = memberRepository.findById(memberId).orElse(null);
                        if(member != null){
                            TeamMemberDTO teamMemberDTO = new TeamMemberDTO();
                            Optional<User> teamMember = userViewRepository.findById(member.getUserId());
                            if(teamMember.isPresent()){
                                teamMemberDTO.setNickname(teamMember.get().getNickname());
                                teamMemberDTO.setExperienceValue(teamMember.get().getExperienceValue());
                                teamMemberDTOS.add(teamMemberDTO);
                            }
                            else {
                                throw new IllegalArgumentException("team member 정보 없음");
                            }
                        }
                        else {
                            throw new IllegalArgumentException("member 정보 없음");
                        }
                    }
                    teamSupportResponseDTO.setMembers(teamMemberDTOS);
                    teamSupportResponseDTO.setRadarPoints(radarPointDTOS);
                    teamSupportResponseDTOS.add(teamSupportResponseDTO);
                }
                else {
                    throw new IllegalArgumentException("team 정보 없음");
                }

            }
            teamSupportListResponseDTO.setResult(teamSupportResponseDTOS);
            return teamSupportListResponseDTO;
        }
        else {
            throw new IllegalArgumentException("잘못된 유저의 접근");
        }
    }
}
