var driversApi = Vue.resource('/busStation/drivers');

Vue.component("driver_row", {
    props: ["driver"],
    template: '<div><i>({{driver.id}})</i> {{driver.name}} {{driver.surname}}</div>'
});

Vue.component("drivers_list", {
    data() {
        return {
            drivers: []
        }
    },
    methods: {
        getDrivers: async function() {
            const result = await driversApi.get()
            this.drivers = await result.json()
        },
    },
    async created(){
        await this.getDrivers()
    },
    template: '<div><driver_row v-for="driver in drivers" :key="driver.id" :driver="driver"/></div>',
});

// Vue.component("driver_4", {
//     created: async function () {
//         const result = await driversApi.query(2)
//         console.log("result with id = 2" + result)
//     }
// });

var app = new Vue({
    el: '#app',
    template: '<drivers_list/>',
});