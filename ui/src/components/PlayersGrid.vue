<template>
    <div>
        <b-form @submit="onSubmit" v-if="show">
            <b-form-input v-model="form.firstName" placeholder="Name"/>
            <b-form-input v-model="form.lastName" placeholder="Surname"/>
            <b-button type="submit" variant="primary">Submit</b-button>
        </b-form>
        <b-table-lite striped hover :items="players"/>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: "PlayersGrid",
        data() {
            return {
                form: {
                    firstName: '',
                    lastName: ''
                },
                players: null,
                show: true
            }
        },
        mounted() {
            this.fetchPlayers()
        },
        methods: {
            fetchPlayers() {
                axios.get(process.env.VUE_APP_URL + "/players")
                    .then(response => (this.players = response.data))
            },
            onSubmit(evt) {
                evt.preventDefault();
                axios.post(process.env.VUE_APP_URL + "/players",
                    {
                        firstName: this.form.firstName,
                        lastName: this.form.lastName
                    }).then(() => {
                    this.fetchPlayers()
                })
            }
        }
    }
</script>

<style scoped>

</style>