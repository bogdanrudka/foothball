<template>
    <div>
        <b-table striped hover :fields="fields" :items="scores"/>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        components: {},
        name: "ScoreGrid",
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
                    {key: 'pivot', sortable: true},
                    {key: 'totalGames', sortable: true},
                ]
            }
        },
        mounted() {
            this.fetchScores()
        },
        methods: {
            fetchScores() {
                axios.get(process.env.VUE_APP_URL + "/scores")
                    .then(response => response.data.map(p => ({
                        id: p.playerId,
                        name: `${p.firstName} ${p.lastName}`,
                        score: p.score,
                        wins: p.wins,
                        loses: p.loses,
                        pivot: Number((p.pivot).toFixed(2)),
                        totalGames: p.total
                    })))
                    .then(response => this.scores = response)
                console.log("Fetched: ", this.scores)
            }
        }
    }
</script>
<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>
<style>
</style>