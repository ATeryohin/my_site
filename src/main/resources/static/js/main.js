var voyagesApi = Vue.resource('/busStation/voyages');

Vue.component("modal_window", {
    template: '' +
        '<div class="my-modal-backdrop">' +
            '<div class="my-modal">' +
                '<slot name="my-header">' +
                '</slot>' +

                '<slot name="my-body">' +
                '</slot>' +
                '<slot name="my-footer">' +
                '</slot>' +
            '</div>' +
        '</div>'
})

Vue.component("voyages_row", {
    props: ["voyage"],
    data(){
        return {
            img_name: "../images/bus_" + ((this.voyage.id % 10) + 1) + ".jpg",
            isModalVisible: false,
            tickets: [],
            selected: null
        }
    },
    created() {
       this.tickets = this.voyage.tickets.filter(item => !item.is_paid);
    },
    methods: {
        showModal() {
            this.isModalVisible = true;
        },
        closeModal() {
            this.isModalVisible = false;
        },
        buyTicket() {
            new_ticket = this.selected
            new_ticket.paid = true
            Vue.resource('/busStation/tickets/' + this.selected.id).update(this.selected, new_ticket)
            this.tickets = this.voyage.tickets.filter(item => item.id != this.selected.id );
            this.isModalVisible = false;
        }
    },
    template:
        '<div class="card" style="min-width: 200px;width: 18rem; margin: 10px;" >' +
        '  <img :src="img_name" class="card-img-top" alt="..." width="320" height="240">' +
        '  <div class="card-body">' +
        '    <h5 class="card-title">{{voyage.info}}</h5>' +
        '    <p class="card-text">' +
                '<div>Номер маршрута: {{voyage.number}}</div>' +
                '<div>Модель автобуса: {{voyage.bus.model}}</div> ' +
                '<div>Номер автобуса: {{voyage.bus.number}}</div> ' +
                '<div>Имя водителя: {{voyage.bus.driver.name}}</div> ' +
            '</p>' +
        '    <button v-on:click="showModal" class="btn btn-primary">Купить билет</button>' +
        '    <div>Количество оставшихся билетов: {{this.voyage.tickets.filter(ticket => !ticket.paid).length}}</div>' +
        '  </div>' +
        '<modal_window v-show="isModalVisible" @close="closeModal"><div slot="my-body">' +
        '  <div class="modal-dialog" style="margin:auto" role="document">\n' +
        '    <div class="modal-content">\n' +
        '      <div class="modal-header">\n' +
        '        <h5 class="modal-title">Маршрут: {{voyage.info}}</h5>\n' +
        '      </div>\n' +
        '      <div class="modal-body">\n' +
        '      <select v-if="tickets.length" v-model="selected"><option v-for="item in tickets" :value="item">Место: {{item.place}}, цена билета: {{item.price}}р.</option></select>' +
        // '        <p>Цена: {{voyage.tickets[0].price}}</p>\n' +
        '      </div>\n' +
        ' <div class="form-group">\n' +
        '    <label for="exampleFormControlInput1">Email address</label>\n' +
        '    <input type="email" class="form-control" id="exampleFormControlInput1" placeholder="name@example.com">\n' +
        '  </div>' +
        '<div class="form-group">\n' +
        '    <label for="exampleFormControlInput1">Номер телефона</label>\n' +
        '    <input type="tel" class="form-control" id="exampleFormControlInput1" placeholder="+790825438547">\n' +
        '  </div>'+
        '<div class="form-group">\n' +
        '    <label for="exampleFormControlInput1">Имя</label>\n' +
        '    <input type="name" class="form-control" id="exampleFormControlInput1" placeholder="Виктор">\n' +
        '  </div>'+
        '      <div class="modal-footer">\n' +
        '        <button type="button" class="btn btn-secondary" data-dismiss="modal" @click="closeModal">Отмена</button>\n' +
        '        <button type="button" class="btn btn-primary" @click="buyTicket">Купить</button>\n' +
        '      </div>\n' +
        '    </div>\n' +
        '  </div>\n' +
        '</div></modal_window>' +
        '</div>',
});

Vue.component("voyages_list", {
    data() {
        return {
            voyages: [],
        }
    },
    methods: {
        getVoyages: async function() {
            const result = await voyagesApi.get()
            this.voyages = await result.json()
        },

    },
    async created(){
        await this.getVoyages()
    },
    template: '<div style="display: flex;flex-wrap: wrap"><voyages_row v-for="voyage in voyages" :key="voyage.id" :voyage="voyage"/></div>',
});

var app = new Vue({
    el: '#app',
    template: '<voyages_list/>',
    methods: {
        create_modal: function(event){
            console.log(event)
        }
    }
});