package com.services;

import com.entity.*;

import com.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class RequestValidator {

    private DriverRepository driverRepository;
    private BusRepository busRepository;
    private TicketRepository ticketRepository;
    private VoyageRepository voyageRepository;

    public void driverRequestValidator(Driver driver) {
        if(driver == null){
            throw new IllegalArgumentException("Driver can't be null");
        }
        if(driver.getLicense() == null || driver.getName() == null || driver.getSurname() == null){
            throw new IllegalArgumentException("Driver fields can't be null");
        }
        if(driver.getId() != null && driverRepository.exists(driver.getId())){
            throw new IllegalArgumentException("Driver with id " + driver.getId() + " exist");
        }
        if(driverRepository.findOneByLicense(driver.getLicense()) != null){
            throw new IllegalArgumentException("Driver with license " + driver.getLicense() + " exist");
        }
    }

    public void busRequestValidator(Bus bus){
        if(bus == null){
            throw new IllegalArgumentException("Bus can't be null");
        }
        if(bus.getNumber() == null || bus.getModel() == null){
            throw new IllegalArgumentException("Bus fields 'number' and 'model' can't be null");
        }
        if(bus.getId() != null && busRepository.exists(bus.getId())){
            throw new IllegalArgumentException("Bus with id " + bus.getId() + " exist");
        }
        if(busRepository.findOneByNumber(bus.getNumber()) != null){
            throw new IllegalArgumentException("Bus with number " + bus.getNumber() + " exist");
        }
    }

    public void busIdDriverIdValidator(Integer busId, Integer driverId){
        if(busId == null || driverId == null){
            throw new IllegalArgumentException("Bus id or Driver id can't be null");
        }
        if(busId <= 0 || driverId <= 0){
            throw new IllegalArgumentException("Bus id or Driver id can't be <= 0");
        }
        if(!driverRepository.exists(driverId) || !busRepository.exists(busId)){
            throw new IllegalArgumentException("Bus with id " + busId + " or Driver with id " + driverId + " not exist");
        }

        Bus bus = busRepository.findOne(busId);
        if(bus.getDriver() != null && bus.getDriver().getId().equals(driverId)){
            throw new IllegalArgumentException("Driver with id " + driverId + " already on Bus " + busId);
        }

        List<Bus> buses = (List<Bus>) busRepository.findAll();
        for (Bus dbBus: buses ) {
            if(dbBus.getDriver() != null) {
                if (dbBus.getDriver().getId().equals(driverId) && !dbBus.getId().equals(busId)) {
                    throw new IllegalArgumentException("Driver " + driverId + " can't be on different Buses");
                }
            }
        }
    }

    public void voyageRequestValidator(Voyage voyage){
        if(voyage == null){
            throw new IllegalArgumentException("Voyage can't be null");
        }
        if(voyage.getNumber() == null){
            throw new IllegalArgumentException("Voyage field 'number' can't be null");
        }
        if(voyage.getId() != null && voyageRepository.exists(voyage.getId())){
            throw new IllegalArgumentException("Voyage with id " + voyage.getId() + " exist");
        }
        if(voyageRepository.findOneByNumber(voyage.getNumber()) != null){
            throw new IllegalArgumentException("Voyage with number " + voyage.getNumber() + " exist");
        }
    }

    public void voyageIdBusIdValidator(Integer voyageId, Integer busId){
        if(voyageId == null || busId == null){
            throw new IllegalArgumentException("Voyage id or Bus id can't be null");
        }
        if(voyageId <= 0 || busId <= 0){
            throw new IllegalArgumentException("Voyage id or Bus id can't be <= 0");
        }
        if(!busRepository.exists(busId) || !voyageRepository.exists(voyageId)){
            throw new IllegalArgumentException("Voyage with id " + voyageId + " or Bus with id " + busId + " not exist");
        }

        Voyage voyage = voyageRepository.findOne(voyageId);
        if(voyage.getBus() != null && voyage.getBus().getId().equals(busId)){
            throw new IllegalArgumentException("Bus with id " + busId + " already on Voyage " + voyageId);
        }

        List<Voyage> voyages = (List<Voyage>) voyageRepository.findAll();
        for (Voyage dbVoyage: voyages ) {
            if(dbVoyage.getBus() != null) {
                if (dbVoyage.getBus().getId().equals(busId) && !dbVoyage.getId().equals(voyageId)) {
                    throw new IllegalArgumentException("Bus " + busId + " can't be on different Voyages");
                }
            }
        }
    }

    public void voyageIdSetOfTicketsValidator(Integer voyageId, Set<Ticket> tickets){
        if(voyageId == null || tickets == null){
            throw new IllegalArgumentException("Voyage id or Set of Tickets can't be null");
        }
        if(voyageId <= 0){
            throw new IllegalArgumentException("Voyage id can't be <= 0");
        }
        if(!voyageRepository.exists(voyageId)){
            throw new IllegalArgumentException("Voyage with id " + voyageId + " not exist");
        }
        if(tickets.size() == 0){
            throw new IllegalArgumentException("No Tickets");
        }

        Voyage dbVoyage = voyageRepository.findOne(voyageId);
        for (Ticket ticket : tickets) {
            if(ticket != null && (ticket.getVoyage() != null || dbVoyage.getTickets() != null)) {
                for(Ticket voyageTicket: dbVoyage.getTickets()){
                    if (voyageTicket.getPlace().equals(ticket.getPlace())) {
                        throw new IllegalArgumentException("Two Tickets with same place can't be");
                    }
                }
            }
        }
    }

    public void voyageIdTicketIdValidator(Integer voyageId, Integer ticketId){
        if(voyageId == null || ticketId == null){
            throw new IllegalArgumentException("Voyage id or Ticket id can't be null");
        }
        if(voyageId <= 0 || ticketId <= 0){
            throw new IllegalArgumentException("Voyage id or Ticket id can't be <= 0");
        }
        if(!voyageRepository.exists(voyageId) || !ticketRepository.exists(ticketId)){
            throw new IllegalArgumentException("Voyage with id " + voyageId + " or Ticket with id " + ticketId + " not exist");
        }

        List<Ticket> tickets = ticketRepository.findAllByVoyage_id(voyageId);
        for (Ticket ticket : tickets) {
            if(ticket.getId().equals(ticketId) && ticket.isPaid()){
                throw new IllegalArgumentException("The Ticket with id " + ticketId + " is already sold");
            }
        }
    }

    @Autowired
    public void setDriverRepository(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Autowired
    public void setBusRepository(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @Autowired
    public void setTicketRepository(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Autowired
    public void setVoyageRepository(VoyageRepository voyageRepository) {
        this.voyageRepository = voyageRepository;
    }
}
