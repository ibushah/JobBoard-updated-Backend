package com.example.jobBoard.Service;

import com.example.jobBoard.Commons.ApiResponse;
import com.example.jobBoard.Dto.FriendDto;
import com.example.jobBoard.Dto.SearchUserDTO;
//import com.example.excelProj.Model.CandidateProfile;
//import com.example.excelProj.Model.CompanyProfile;
import com.example.jobBoard.Model.Friend;
import com.example.jobBoard.Model.Profile;
import com.example.jobBoard.Model.User;
//import com.example.excelProj.Repository.CandidateProfileRepository;
//import com.example.excelProj.Repository.CompanyProfileRepository;
import com.example.jobBoard.Repository.FriendRepository;
import com.example.jobBoard.Repository.ProfileRepository;
import com.example.jobBoard.Repository.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class FriendService {


    @Autowired
    FriendRepository friendRepository;


    @Autowired
    UserDaoRepository userDaoRepository;

    @Autowired
    ProfileRepository profileRepository;

//    @Autowired
//    CandidateProfileRepository candidateProfileRepository;
//
//    @Autowired
//    CompanyProfileRepository companyProfileRepository;

    public ApiResponse sendRequest(FriendDto friendsIdDto) {
        Long friendId = 1l;
        if (friendsIdDto.getType().equals("candidate")) {
            Optional<Profile> candidateProfile = profileRepository.findById(friendsIdDto.getFriendId());
            if (candidateProfile.isPresent())
                friendId = candidateProfile.get().getUser().getId();

        } else if (friendsIdDto.getType().equals("employer")){
            Optional<Profile> companyProfile = profileRepository.findById(friendsIdDto.getFriendId());
            if (companyProfile.isPresent())
                friendId = companyProfile.get().getUser().getId();

        }
        else
            friendId=friendsIdDto.getFriendId();

        Optional<User> user = userDaoRepository.findById(friendsIdDto.getUserId());
        Optional<User> friend = userDaoRepository.findById(friendId);

        if (user.isPresent() && friend.isPresent()) {
            friendRepository.save(new Friend(user.get(), friend.get(), "pending"));
            friendRepository.save(new Friend(friend.get(), user.get(), "approval"));
            return new ApiResponse<>(200, "Friend request sent", null);
        }
        return new ApiResponse<>(400, "An Error occured while sending th request", null);

    }


    public ApiResponse acceptRequest(FriendDto friendsIdDto) {
        Long friendId = 1l;
        if (friendsIdDto.getType().equals("candidate")) {
            Optional<Profile> candidateProfile = profileRepository.findById(friendsIdDto.getFriendId());
            if (candidateProfile.isPresent())
                friendId = candidateProfile.get().getUser().getId();

        } else if (friendsIdDto.getType().equals("employer")){
            Optional<Profile> companyProfile = profileRepository.findById(friendsIdDto.getFriendId());
            if (companyProfile.isPresent())
                friendId = companyProfile.get().getUser().getId();

        }
        else friendId=friendsIdDto.getFriendId();
        Optional<User> user = userDaoRepository.findById(friendsIdDto.getUserId());
        Optional<User> friend = userDaoRepository.findById(friendId);

        if (user.isPresent() && friend.isPresent()) {

            Friend friend1 = friendRepository.findByFriendAndUser(friendsIdDto.getUserId(), friendId);
            Friend friend2 = friendRepository.findByUserAndFriend(friendsIdDto.getUserId(), friendId);
            friend1.setStatus("accepted");
            friend2.setStatus("accepted");

            if (friend1 != null) {
                try {
                    friendRepository.saveAll(
                            Arrays.asList(friend2,friend1));
                    return new ApiResponse(200, "Friend Request accepted", null);

                } catch (Exception e) {
                    return new ApiResponse(400, "An error occured", null);
                }
            }
            return new ApiResponse(400, "Something went wrong", null);
        }
        return new ApiResponse(400, "Something went wrong", null);
    }

    public List<SearchUserDTO> getAllRequests(Long userId) {
        List<Friend> friends = friendRepository.findAllUserRequests(userId);
        List<SearchUserDTO> searchUserDTOList = new ArrayList<>();

        friends.forEach((friend) -> {
            if (!friend.getUser().getUserType().equals("candidate")) {
                searchUserDTOList.add(new SearchUserDTO(friend.getUser().getName(),
                        friend.getUser().getUserType(),
                        friend.getUser().getId(),
                        friend.getUser().getProfile().getId(),
                        friend.getUser().getProfile().getDp()
                ));
            } else if(friend.getUser().getProfile()!=null){
                searchUserDTOList.add(new SearchUserDTO(friend.getUser().getName(),
                        friend.getUser().getUserType(),
                        friend.getUser().getId(),
                        friend.getUser().getProfile().getId(),
                        friend.getUser().getProfile().getDp()
                ));
            }
            else{
                searchUserDTOList.add(new SearchUserDTO(friend.getUser().getName(),
                        friend.getUser().getUserType(),
                        friend.getUser().getId(),
                        null,
                        null)
                );
            }
        });
        return searchUserDTOList;
    }


    public ApiResponse removeFriend(FriendDto friendsIdDto) {

        Long friendId = 1l;
        if (friendsIdDto.getType().equals("candidate")) {
            Optional<Profile> candidateProfile = profileRepository.findById(friendsIdDto.getFriendId());
            if (candidateProfile.isPresent())
                friendId = candidateProfile.get().getUser().getId();

        }
        else if (friendsIdDto.getType().equals("employer"))
        {
            Optional<Profile> companyProfile = profileRepository.findById(friendsIdDto.getFriendId());
                if (companyProfile.isPresent())
                friendId = companyProfile.get().getUser().getId();

        }
        else {
            friendId=friendsIdDto.getFriendId();
        }
        friendRepository.removeFriend(friendsIdDto.getUserId(), friendId);
        return new ApiResponse(200, "Request Canceled", null);
    }

    public ResponseEntity<String> getFriendshipStatus(FriendDto friendsIdDto) {
        Long friendId = 1l;
        if (friendsIdDto.getType().equals("candidate")) {
            friendId = friendsIdDto.getFriendId();
            Optional<Profile> candidateProfile = profileRepository.findById(friendsIdDto.getFriendId());
            if (candidateProfile.isPresent())
                friendId = candidateProfile.get().getUser().getId();

        } else if (friendsIdDto.getType().equals("employer")){
//                    friendId = friendsIdDto.getFriendId();
            Optional<Profile> companyProfile = profileRepository.findById(friendsIdDto.getFriendId());
            if (companyProfile.isPresent())
                friendId = companyProfile.get().getUser().getId();
        }
        else {
            friendId=friendsIdDto.getFriendId();
        }

        if (friendsIdDto.getUserId() == friendId)
            return new ResponseEntity<>("\"same\"", HttpStatus.OK);

        Friend friend = friendRepository.findByUserAndFriend(friendsIdDto.getUserId(), friendId);

        if (friend == null) {
            Friend friend1 = friendRepository.findByFriendAndUser(friendsIdDto.getUserId(), friendId);
            if(friend1==null)
            return new ResponseEntity<>("\"notFriends\"", HttpStatus.OK);
            else return new ResponseEntity<>("\""+friend1.getStatus()+"\"", HttpStatus.OK);
        }
        else  return new ResponseEntity<>("\""+friend.getStatus()+"\"", HttpStatus.OK);
    }

    public ResponseEntity getAllFriends(Long id) {
        List<Friend> friends = friendRepository.findAllFriends(id);
        List<SearchUserDTO> searchUserDTOList = new ArrayList<>();

        friends.forEach((friend) -> {
            if (!friend.getFriend().getUserType().equals("candidate")) {
                searchUserDTOList.add(new SearchUserDTO(friend.getFriend().getName(),
                        friend.getFriend().getUserType(),
                        friend.getFriend().getId(),
                        friend.getFriend().getProfile().getId(),
                        friend.getFriend().getProfile().getDp()
                ));
            } else {
                searchUserDTOList.add(new SearchUserDTO(friend.getFriend().getName(),
                        friend.getFriend().getUserType(),
                        friend.getFriend().getId(),
                        friend.getFriend().getProfile().getId(),
                        friend.getFriend().getProfile().getDp()
                ));
            }
        });
        return  new ResponseEntity(searchUserDTOList,HttpStatus.OK);
    }


}
