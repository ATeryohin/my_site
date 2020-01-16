package com.controllers;

import com.entity.Ticket;
import com.entity.Voyage;

import com.services.VoyageService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/busStation")
public class VoyageController {

    private VoyageService service;

    /**
     * Этот метод создает запись 'Рейс' в БД и возвращает эту запись с присвоенным ID.
     *
     * request{
     *     "number":"..." - номер рейса (String)
     * }
     * response{
     *     "id": ... - ID созданной записи
     *     "number":"..." - номер рейса созданной записи
     *     "bus":null - автобус созданной записи
     *     "tickets":null- список билетов на рейс созданной записи
     * }
     *
     * пример curl запроса:
     * curl -H "Content-type: application/json" -X POST -d '{"number":"ERES1"}' http://localhost:8090/busStation/voyages
     */

    @RequestMapping(value = "/voyages", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addVoyage(@RequestBody Voyage voyage) {
        return ResponseEntity.ok(service.addVoyage(voyage));
    }

    /**
     * Этот метод по {id} конкретного 'Рейса' в БД присваевает по {id} конкретный 'Автобус' в БД
     * и возвращает измененную запись конкретного 'Рейса'.
     *
     * response{
     *     "id": ... - ID запрашиваемой записи
     *     "number":"..." - номер рейса запрашиваемой записи
     *     "bus":{
     *              "id": ... - ID хранимого автобуса
     *              "number":"..." - номер хранимого автобуса
     *              "model":"..." - модель хранимого автобуса
     *              "driver":null - если хранимый автобус не хранит 'Водителя'
     *                      {
     *                          "id": ... - ID хранимого водителя
     *                          "license":"..." - номер водилельского удостоверения хранимого водителя
     *                          "name":"..." - имя водителя хранимого водителя
     *                          "surname":"..." - фамилия водителя хранимого водителя
     *                      } - если к хранимый автобус хранит 'Водителя'
     *          }
     *     "tickets":null- если запрашиваемая запись не хранит 'Билеты'
     *              [{
     *               "id": ... - ID хранимого билета
     *               "place": ...- место хранимого билета
     *               "price": ... -  цена хранимого билета
     *               "isPaid": ... - true(если билет оплачен) или false(если билет не оплачен) хранимого билета
     *              }] - если запрашиваемая запись хранит 'Билеты'
     * }
     *
     * пример curl запроса:
     * curl -H "Content-type: application/json" -X PUT http://localhost:8090/busStation/voyages/{id}/buses/{busId}
     */

    @RequestMapping(value = "/voyages/{id}/buses/{busId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> changeBusOnVoyage( @PathVariable(value="id") Integer voyageId,
                                                @PathVariable(value="busId") Integer busId) {
        return ResponseEntity.ok(service.changeBusOnVoyage(voyageId, busId));
    }

    /**
     * Этот метод по {id} конкретного 'Рейса' в БД создает и присваевает/добавляет один 'Билет'
     * и возвращает измененную запись конкретного 'Рейса'.
     *
     * request{
     *     "place":"..." - место билета (String)
     *     "price": ... - цена билета (Integer)
     * }
     * response{
     *     "id": ... - ID запрашиваемой записи
     *     "number":"..." - номер рейса запрашиваемой записи
     *     "bus":null - если запрашиваемая запись не хранит 'Автобус'
     *          {
     *              "id": ... - ID хранимого автобуса
     *              "number":"..." - номер хранимого автобуса
     *              "model":"..." - модель хранимого автобуса
     *              "driver":null - если хранимый автобус не хранит 'Водителя'
     *                      {
     *                          "id": ... - ID хранимого водителя
     *                          "license":"..." - номер водилельского удостоверения хранимого водителя
     *                          "name":"..." - имя водителя хранимого водителя
     *                          "surname":"..." - фамилия водителя хранимого водителя
     *                      } - если к хранимый автобус хранит 'Водителя'
     *          } - если запрашиваемая запись хранит 'Автобус'
     *     "tickets":
     *              [{
     *               "id": ... - ID хранимого билета
     *               "place": ...- место хранимого билета
     *               "price": ... -  цена хранимого билета
     *               "isPaid": ... - true(если билет оплачен) или false(если билет не оплачен) хранимого билета
     *              }] - билеты конкретной запрашиваемой записи
     * }
     *
     * пример curl запроса:
     * curl -H "Content-type: application/json" -X POST -d '{"place":1, "price":20}' http://localhost:8090/busStation/voyages/{id}/ticket
     */

    @RequestMapping(value = "/voyages/{id}/ticket", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addTicketOnVoyage( @PathVariable(value="id") Integer voyageId,
                                                @RequestBody Ticket ticket) {
        Set<Ticket> tickets = new HashSet<>();
        tickets.add(ticket);
        return ResponseEntity.ok(service.addTicketsOnVoyage(voyageId, tickets));
    }

    /**
     * Этот метод по {id} конкретного 'Рейса' в БД создает и присваевает/добавляет несколько 'Билетов'
     * и возвращает измененную запись конкретного 'Рейса'.
     *
     * request[
     *      {
     *          "place":"..." - место билета (String)
     *          "price": ... - цена билета (Integer)
     *      },
     *      {
     *          "place":"..." - место билета (String)
     *          "price": ... - цена билета (Integer)
     *      },
     *      ...
     * ]
     * response{
     *     "id": ... - ID запрашиваемой записи
     *     "number":"..." - номер рейса запрашиваемой записи
     *     "bus":null - если запрашиваемая запись не хранит 'Автобус'
     *          {
     *              "id": ... - ID хранимого автобуса
     *              "number":"..." - номер хранимого автобуса
     *              "model":"..." - модель хранимого автобуса
     *              "driver":null - если хранимый автобус не хранит 'Водителя'
     *                      {
     *                          "id": ... - ID хранимого водителя
     *                          "license":"..." - номер водилельского удостоверения хранимого водителя
     *                          "name":"..." - имя водителя хранимого водителя
     *                          "surname":"..." - фамилия водителя хранимого водителя
     *                      } - если к хранимый автобус хранит 'Водителя'
     *          } - если запрашиваемая запись хранит 'Автобус'
     *     "tickets":
     *              [{
     *               "id": ... - ID хранимого билета
     *               "place": ...- место хранимого билета
     *               "price": ... -  цена хранимого билета
     *               "isPaid": ... - true(если билет оплачен) или false(если билет не оплачен) хранимого билета
     *              }] - билеты конкретной запрашиваемой записи
     * }
     *
     * пример curl запроса:
     * curl -H "Content-type: application/json" -X POST -d '[{"place":1, "price":20}, {"place":2, "price":20}]' http://localhost:8090/busStation/voyages/{id}/tickets
     */

    @RequestMapping(value = "/voyages/{id}/tickets", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?>  addTicketsOnVoyage( @PathVariable(value="id") Integer voyageId,
                                                  @RequestBody ArrayList<Ticket> tickets) {

        Set<Ticket> ticketSet = new HashSet<>(tickets);
        return ResponseEntity.ok(service.addTicketsOnVoyage(voyageId, ticketSet));
    }

    /**
     * Этот метод по {id} конкретного 'Рейса' в БД и по {id} конкретный 'Билета' этого 'Рейса' меняет поле "isPaid" на true
     * и возвращает описание 'Рейса' после покупки билета.
     *
     * response{
     *      Voyage{
     *          number = ... - номер рейса
     *          busNumber = ... - номер автобуса на рейсе
     *          busModel = ... - модель атобуса на рейсе
     *          driverName = ... - имя водителя автобуса
     *          driverSurname = ... - фамилия водителя автобуса
     *          ticketPrice = ... - стоимость билета
     *          ticketPlace = ... - место билета
     *          isPaid = true - состояние билета (оплачен)
     * }
     *
     * пример curl запроса:
     * curl -H "Content-type: application/json" -X PUT http://localhost:8090/busStation/voyages/{id}/tickets/{ticketId}
     */

    @RequestMapping(value = "/voyages/{id}/tickets/{ticketId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?>  sellTicket( @PathVariable(value="id") Integer voyageId,
                                          @PathVariable(value="ticketId") Integer ticketId) {
        return ResponseEntity.ok(service.sellTicket(voyageId, ticketId));
    }

    /**
     * Этот метод по {id} извлекает конкретный 'Рейс'
     *
     * response{
     *     "id": ... - ID запрашиваемой записи
     *     "number":"..." - номер рейса запрашиваемой записи
     *     "bus":null - если запрашиваемая запись не хранит 'Автобус'
     *          {
     *              "id": ... - ID хранимого автобуса
     *              "number":"..." - номер хранимого автобуса
     *              "model":"..." - модель хранимого автобуса
     *              "driver":null - если хранимый автобус не хранит 'Водителя'
     *                      {
     *                          "id": ... - ID хранимого водителя
     *                          "license":"..." - номер водилельского удостоверения хранимого водителя
     *                          "name":"..." - имя водителя хранимого водителя
     *                          "surname":"..." - фамилия водителя хранимого водителя
     *                      } - если к хранимый автобус хранит 'Водителя'
     *          } - если запрашиваемая запись хранит 'Автобус'
     *     "tickets":null- если запрашиваемая запись не хранит 'Билеты'
     *              [{
     *               "id": ... - ID хранимого билета
     *               "place": ...- место хранимого билета
     *               "price": ... -  цена хранимого билета
     *               "isPaid": ... - true(если билет оплачен) или false(если билет не оплачен) хранимого билета
     *              }] - если запрашиваемая запись хранит 'Билеты'
     * }
     *
     * пример curl запроса:
     * curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/voyages/{id}
     */

    @RequestMapping(value = "/voyages/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findOneVoyage(@PathVariable(value="id") Integer id) {
        return ResponseEntity.ok(service.findOne(id));
    }

    /**
     * Этот метод извлекает список всех 'Рейсов'
     *
     * response[{...}]
     *
     * пример curl запроса:
     * curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/voyages
     */

    @RequestMapping(value = "/voyages", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findAllVoyages() {
        return ResponseEntity.ok(service.findAll());
    }

    @Autowired
    public void setService(VoyageService service) {
        this.service = service;
    }
}
