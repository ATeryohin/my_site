package com.controllers;

import com.entity.Bus;

import com.services.BusService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/busStation")
public class BusController {

    private BusService service;

    /**
     * Этот метод создает запись 'Автобус' в БД и возвращает эту запись с присвоенным ID.
     *
     * request{
     *     "number":"..." - номер автобуса (String)
     *     "model":"..." - модель автобуса (String)
     * }
     * response{
     *     "id": ... - ID созданной записи
     *     "number":"..." - номер автобуса созданной записи
     *     "model":"..." - модель автобуса созданной записи
     *     "driver":null - 'Водитель' созданной записи
     * }
     *
     * пример curl запроса:
     * curl -H "Content-type: application/json" -X POST -d '{"number":"AA9898II", "model":"Ferrari"}' http://localhost:8090/busStation/buses
     */

    @RequestMapping(value = "/buses", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addBus(@RequestBody Bus bus) {
        return ResponseEntity.ok(service.addBus(bus));
    }

    /**
     * Этот метод по {id} конкретного 'Автобуса' в БД присваевает/меняет по {id} конкретного 'Водителя' в БД.
     * и возвращает измененную запись конкретного 'Автобуса'.
     *
     * response{
     *     "id": ... - ID автобуса конкретной записи
     *     "number":"..." - номер автобуса конкретной записи
     *     "model":"..." - модель автобуса конкретной записи
     *     "driver": {
     *                  "id": ... - ID водителя конкретной записи
     *                  "license":"..." - номер водилельского удостоверения водителя конкретной записи
     *                  "name":"..." - имя водителя водителя конкретной записи
     *                  "surname":"..." - фамилия водителя водителя конкретной записи
     *              } - 'Водитель' конкретной записи
     * }
     *
     * пример curl запроса:
     * curl -H "Content-type: application/json" -X PUT http://localhost:8090/busStation/buses/{id}/drivers/{driverId}
     */

    @RequestMapping(value = "/buses/{id}/drivers/{driverId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> changeDriverOnBus( @PathVariable(value="id") Integer busId,
                                                @PathVariable(value="driverId") Integer driverId) {
        return ResponseEntity.ok(service.changeDriverOnBus(busId, driverId));
    }

    /**
     * Этот метод по {id} извлекает конкретный 'Автобус'
     *
     * response{
     *     "id": ... - ID запрашиваемой записи
     *     "number":"..." - номер автобуса запрашиваемой записи
     *     "model":"..." - модель автобуса запрашиваемой записи
     *     "driver":null - если запрашиваемая запись не хранит 'Водителя'
     *              {
     *                  "id": ... - ID хранимого водителя
     *                  "license":"..." - номер водилельского удостоверения хранимого водителя
     *                  "name":"..." - имя водителя хранимого водителя
     *                  "surname":"..." - фамилия водителя хранимого водителя
     *              } - если к запрашиваемая запись хранит 'Водителя'
     * }
     *
     * пример curl запроса:
     * curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/buses/{id}
     */

    @RequestMapping(value = "/buses/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findOneBus(@PathVariable(value="id") Integer id) {
        return ResponseEntity.ok(service.findOne(id));
    }

    /**
     * Этот метод извлекает список всех 'Автобусов'
     *
     * response[{...}]
     *
     * пример curl запроса:
     * curl -H "Content-type: application/json" -X GET http://localhost:8090/busStation/buses
     */

    @RequestMapping(value = "/buses", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findAllBuses() {
        return ResponseEntity.ok(service.findAll());
    }

    @Autowired
    public void setService(BusService service) {
        this.service = service;
    }
}
