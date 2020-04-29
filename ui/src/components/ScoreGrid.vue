<template>
    <div>
        <b-table striped hover :fields="fields" :items="scores">
            <!-- A custom formatted column -->
            <template v-slot:cell(progress)="data">
                <b-progress height="2em" class="mt-0" :max="maxGames" show-value>
                    <b-progress-bar :value="data.item.wins" variant="success"></b-progress-bar>
                    <b-progress-bar :value="data.item.totalGames - data.item.wins" variant="danger"></b-progress-bar>
                    <b-progress-bar :value="104 - data.item.totalGames" variant="info" :striped="true"></b-progress-bar>
                </b-progress>
            </template>

        </b-table>
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
                maxGames: 104,
                players: [],
                value: [],
                scores: [],
                show: true,
                fields: [
                    {key: 'name', sortable: true},
                    {key: 'progress', sortable: false, tdClass: 'progressCol'},
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
    .progressCol {
        width: 40rem;
    }
</style>