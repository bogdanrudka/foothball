<template>
    <div>
        <multiselect v-model="value"
                     :fields="fields"
                     :options="players"
                     @remove="removeScore"
                     @close="fetchScores"
                     :multiple="true"
                     :close-on-select="false"
                     :clear-on-select="false"
                     :preserve-search="true" placeholder="Pick some" label="name"
                     track-by="id" :preselect-first="true">
        </multiselect>
        <b-table striped hover :fields="fields" :items="scores"/>
    </div>
</template>

<script>
    import axios from "axios";
    import Multiselect from 'vue-multiselect'

    export default {
        components: {
            Multiselect
        },
        name: "Games",
        data() {
            return {
                form: {
                    firstName: '',
                    lastName: ''
                },
                players: [],
                value: [],
                scores: [],
                show: true,
                fields: [
                    {key: 'name', sortable: true},
                    {key: 'score', sortable: true},
                    {key: 'wins', sortable: true},
                    {key: 'loses', sortable: true},
                    {key: 'totalGames', sortable: true},
                ]
            }
        },
        mounted() {
            this.fetchPlayers()
        },
        methods: {
            fetchPlayers() {
                axios.get(process.env.VUE_APP_URL + "/players")
                    .then(response => response.data.map(p => ({id: p.id, name: `${p.firstName} ${p.lastName}`})))
                    .then(response => this.players = response)
            },
            removeScore(removedOption) {
                this.scores = this.scores.filter(value => value.id !== removedOption.id)
            },
            fetchScores() {
                if (this.value.length === 0) {
                    this.scores = []
                    return
                }
                console.log("Fetching scored: ", this.value)
                let request = new URLSearchParams();
                this.value.map(p => request.append("ids", p.id))

                axios.get(process.env.VUE_APP_URL + "/players/scores", {params: request})
                    .then(response => response.data.map(p => ({
                        id: p.playerId,
                        name: `${p.firstName} ${p.lastName}`,
                        score: p.score,
                        wins: p.wins,
                        loses: p.loses,
                        totalGames: p.total
                    })))
                    .then(response => this.scores = response)
                console.log("Fetched: ", this.scores)
            }
        }
    }
</script>
<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>
