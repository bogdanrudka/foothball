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
                    {key: 't1_goalkeeper', sortable: true},
                    {key: 't1_strikers', sortable: true},
                    {key: 't1_score', sortable: true},
                    {key: 't2_score', sortable: true},
                    {key: 't2_goalkeeper', sortable: true},
                    {key: 't2_strikers', sortable: true},
                ]
            }
        },
        mounted() {
            if (this.players.length === 0 || this.scores.length === 0) {
                axios.get(process.env.VUE_APP_URL + "/players")
                    .then(response => response.data.map(p => ({id: p.id, name: `${p.firstName} ${p.lastName}`})))
                    .then(response => this.players = response)
                    .then(() => this.fetchScores())
            }
        },
        methods: {
            removeScore(removedOption) {
                this.scores = this.scores.filter(value => value.id !== removedOption.id)
            },
            playerToName(player) {
                return `${player.firstName} ${player.lastName}`
            },
            fetchScores() {
                let request = new URLSearchParams();
                if (this.value.length === 0) {
                    this.players.forEach(p => request.append("players", p.id))
                } else {
                    this.value.forEach(p => request.append("players", p.id))
                }

                axios.get(process.env.VUE_APP_URL + "/games", {params: request})
                    .then(response => response.data.map(g => ({
                        id: g.id,
                        t1_goalkeeper: this.playerToName(g.scores[0].team.players[0]),
                        t1_strikers: this.playerToName(g.scores[0].team.players[1]),
                        t1_score: g.scores[0].score,
                        t2_score: g.scores[1].score,
                        t2_goalkeeper: this.playerToName(g.scores[1].team.players[0]),
                        t2_strikers: this.playerToName(g.scores[1].team.players[1]),
                    })))
                    .then(response => this.scores = response)
                console.log("Fetched: ", this.scores)
            }
        }
    }
</script>
<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>
