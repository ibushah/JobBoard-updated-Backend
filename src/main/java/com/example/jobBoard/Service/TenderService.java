package com.example.jobBoard.Service;

//import com.example.jobBoard.Dto.CompanyProfileDTO;
import com.example.jobBoard.Dto.TenderDTO;
import com.example.jobBoard.Dto.ViewTenderDTO;
import com.example.jobBoard.Model.Tender;
import com.example.jobBoard.Model.TenderAssortments;
import com.example.jobBoard.Model.User;
import com.example.jobBoard.Repository.TenderAssortmentRepository;
import com.example.jobBoard.Repository.TenderRepository;
import com.example.jobBoard.Repository.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.View;
import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TenderService {

    @Autowired
    TenderRepository tenderRepository;

    @Autowired
    UserDaoRepository userDaoRepository;

    @Autowired
    TenderAssortmentRepository tenderAssortmentRepository;

    public ResponseEntity<Tender> save(TenderDTO tenderDTO) {
        try {
            Tender tender = new Tender();
            if(tenderDTO!=null){
                tender.setRole(tenderDTO.getRole());
                tender.setAddress(tenderDTO.getAddress());
                tender.setCategory(tenderDTO.getCategory());
                tender.setCity(tenderDTO.getCity());
                tender.setCountry(tenderDTO.getCountry());
                tender.setActive(true);
                tender.setDescription(tenderDTO.getDescription());
                tender.setInterviewStartDate(tenderDTO.getInterviewStartDate());
                tender.setProvince(tenderDTO.getProvince());
                tender.setInterviewEndDate(tenderDTO.getInterviewEndDate());
                tender.setInterviewStartTiming(tenderDTO.getInterviewStartTiming());
                tender.setInterviewEndTiming(tenderDTO.getInterviewEndTiming());
                tender.setLatitude(tenderDTO.getLatitude());
                tender.setLongitude(tenderDTO.getLongitude());
                tender.setTenderType(tenderDTO.getTenderType());
                tender.setSalary(tenderDTO.getSalary());
                tender.setType(tenderDTO.getType());
                tender.setTenderPoster(userDaoRepository.findById(tenderDTO.getEmployerUserId()).get());
                tenderRepository.save(tender);
                if(tenderDTO.getTenderType().equalsIgnoreCase("private")) saveInTenderAssortMents(tender,tenderDTO,"employer","recruiter");
                return new ResponseEntity<Tender>(tender != null ? tender : null, HttpStatus.OK);
            }
        }
        catch (Exception e){
            return  new ResponseEntity<Tender>(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<Tender>(HttpStatus.NOT_FOUND);
    }


    //view tender
    public ResponseEntity<ViewTenderDTO> findTenderById(Long tenderId) {
        try {
            Tender tenderOption = tenderRepository.findTender(tenderId);
            TenderDTO tenderDTO = new TenderDTO(tenderOption);
            Optional<User> user = userDaoRepository.findById(tenderOption.getTenderPoster().getId());
            ViewTenderDTO viewTenderDTO = new ViewTenderDTO();
            viewTenderDTO.setTenderDTO(tenderDTO);
            viewTenderDTO.setUserDto(user.get());
            return new ResponseEntity<ViewTenderDTO>(viewTenderDTO, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<ViewTenderDTO>(HttpStatus.NOT_FOUND);
        }
    }

    //  send notifcations about all tenders to recruiter
    public ResponseEntity<List<TenderAssortments>> getTenderNotifications(Long recruiterId){
        List<TenderAssortments> listOfNotifications =tenderAssortmentRepository.getTenderNotifications(recruiterId);
        return new ResponseEntity<List<TenderAssortments>>(listOfNotifications, HttpStatus.OK);
    }



//    @Transactional
    public ResponseEntity acceptOrDeclineTender(TenderDTO tenderDTO,Boolean isAccept){
            //accept tender work  and decline tender work
            try{
                //i need tender id and recruiter userId and a boolean if he accepts or decline so that i can send a notifaction
                //update tenderAssortment
                //public tenders ke lye create a tender assortment first
                if(tenderDTO.getTenderType().equalsIgnoreCase("public")){
                    TenderAssortments tenderAssortments = new TenderAssortments();
                    tenderAssortments.setApplied(isAccept==true?true:false);
                    tenderAssortments.setSeen(true);
                    tenderAssortments.setNotificationFrom("recruiter");
                    tenderAssortments.setNotificationFor("employer");
                    tenderAssortments.setNotificationDate(new Date());
                    User employerUser = userDaoRepository.findById(tenderDTO.getEmployerUserId()).get();
                    User recruiterUser = userDaoRepository.findById(tenderDTO.getRecruiterUserId()).get();
                    Optional<Tender> tender1 = tenderRepository.findById(tenderDTO.getId());
                    tenderAssortments.setEmployer(employerUser);
                    tenderAssortments.setRecruiter(recruiterUser);
                    tenderAssortments.setTender(tender1.get());
                    tenderAssortmentRepository.save(tenderAssortments);


                }
                else{
                    if(isAccept==true){
                        //tenderAccepted
                        tenderAssortmentRepository.updateTender(isAccept,tenderDTO.getId(),tenderDTO.getRecruiterUserId(),tenderDTO.getEmployerUserId());
                    }
                    else{
                        tenderAssortmentRepository.updateTender(isAccept,tenderDTO.getId(),tenderDTO.getRecruiterUserId(),tenderDTO.getEmployerUserId());
                    }
                }


                //send backNotificaiton to employer whatever he does;


                return new ResponseEntity<>(HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }


    public ResponseEntity<List<TenderAssortments>> sendNotificationToEmployerAboutAcceptOrDecline(Long employerUserId){
            //find all the tender which recruiter accepts or decline;
        List<TenderAssortments> listOfNotifications =tenderAssortmentRepository.getAcceptOrDeclineNotification(employerUserId);
        return new ResponseEntity<List<TenderAssortments>>(listOfNotifications, HttpStatus.OK);

    }

    public void saveInTenderAssortMents(Tender tender,TenderDTO tenderDTO,String notificationFrom,String notificationFor){
        //
        TenderAssortments tenderAssortments = new TenderAssortments();
        User employerUser = userDaoRepository.findById(tenderDTO.getEmployerUserId()).get();
        if(tenderDTO.getRecruiterUserId()!=null) {
            User recruiterUser = userDaoRepository.findById(tenderDTO.getRecruiterUserId()).get();
            tenderAssortments.setRecruiter(recruiterUser);
        }else{
            User recruiterUser=null;
            tenderAssortments.setRecruiter(recruiterUser);
        }
            Tender tender1 = tenderRepository.findById(tender.getId()).get();

        if(employerUser!=null  && tender1!=null){

            tenderAssortments.setEmployer(employerUser);

            tenderAssortments.setTender(tender);
            tenderAssortments.setNotificationDate(new Date());
            tenderAssortments.setNotificationFrom(notificationFrom);
            tenderAssortments.setNotificationFor(notificationFor);
            tenderAssortments.setSeen(false);
            tenderAssortments.setApplied(false);
            tenderAssortmentRepository.save(tenderAssortments);
        }
    }
}
