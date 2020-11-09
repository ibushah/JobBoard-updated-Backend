package com.example.jobBoard.Service;

import com.example.jobBoard.Dto.ChatroomDTO;
import com.example.jobBoard.Dto.SearchUserDTO;
import com.example.jobBoard.Model.Chat;
import com.example.jobBoard.Model.Chatroom;
import com.example.jobBoard.Model.User;
import com.example.jobBoard.Repository.ChatRepository;
import com.example.jobBoard.Repository.ChatroomRepository;
import com.example.jobBoard.Repository.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatService {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    ChatroomRepository chatroomRepository;

    @Autowired
    UserDaoRepository userDaoRepository;


    public ResponseEntity<String> initiateChat(Long user1Id, Long user2Id) {
        Chatroom chatroom = chatroomRepository.findChatroom(user1Id, user2Id);

        if (chatroom == null) {
            String uuid = UUID.randomUUID().toString();
            Optional<User> user1 = userDaoRepository.findById(user1Id);
            Optional<User> user2 = userDaoRepository.findById(user2Id);

            if (user1.isPresent() && user2.isPresent()) {
                chatroomRepository.save(new Chatroom(user1.get(), user2.get(), uuid));
                chatroomRepository.save(new Chatroom(user2.get(), user1.get(), uuid));

                return new ResponseEntity<>("\"" + uuid + "\"", HttpStatus.OK);

            }
            return new ResponseEntity<>("\"Chat users not found\"", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("\"" + chatroom.getChatroomId() + "\"", HttpStatus.OK);
    }


    public ResponseEntity<List<Chat>> getAllChats(String chatroomId, Long freindId) {
        chatRepository.setSeenMessage(chatroomId, freindId);
        return new ResponseEntity(chatRepository.findAllChats(chatroomId), HttpStatus.OK);
    }

    public ResponseEntity seenAllUserChatroomMessages(String chatroomId, Long id) {
        chatRepository.setSeenMessage(chatroomId, id);
        return new ResponseEntity("\"Messages seen\"", HttpStatus.OK);
    }

    public ResponseEntity<SearchUserDTO> getAllChatrooms(Long id)
    {
       List<ChatroomDTO> chatroomDTOList=chatroomRepository.findChatrooms(id);

        List<SearchUserDTO> searchUserDTOList = new ArrayList<>();

        chatroomDTOList.forEach((chatroom) -> {
            if (!chatroom.getUser().getUserType().equals("candidate")) {
                searchUserDTOList.add(new SearchUserDTO(chatroom.getUser().getName(),
                        chatroom.getUser().getUserType(),
                        chatroom.getUser().getId(),
                        chatroom.getUser().getProfile().getId(),
                        chatroom.getUser().getProfile().getDp(),
                        chatroom.getMessage(),
                        chatroom.getSeen(),
                        chatroom.getDate(),
                        chatroom.getSender()
                ));
            } else {
                searchUserDTOList.add(new SearchUserDTO(chatroom.getUser().getName(),
                        chatroom.getUser().getUserType(),
                        chatroom.getUser().getId(),
                        chatroom.getUser().getProfile().getId(),
                        chatroom.getUser().getProfile().getDp(),
                        chatroom.getMessage(),
                        chatroom.getSeen(),
                        chatroom.getDate(),
                        chatroom.getSender()
                ));
            }
        });
        return new ResponseEntity(searchUserDTOList,HttpStatus.OK);
    }


    public ResponseEntity getChatCount(Long id){
        List<ChatroomDTO> chatroomDTOList=chatroomRepository.findChatrooms(id);

        Long count= chatroomDTOList
                .stream()
                .filter((chatroomDTO)->chatroomDTO.getSender()!=id && chatroomDTO.getSeen().equals(false))
                .count();
        return new ResponseEntity(count, HttpStatus.OK);


    }
}
