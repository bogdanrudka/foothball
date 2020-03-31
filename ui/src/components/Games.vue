<!--suppress ALL -->
<template>
    <b-container fluid>

        <b-row class="text-center">
            <b-col cols="2">
                <b-form-radio-group
                        id="btn-radios-1"
                        v-model="selectedGateTypes"
                        :options="gameTypes"
                        @input="fetchGames"
                        buttons
                        name="radios-btn-default"
                />
            </b-col>
            <b-col cols="7">
                <multiselect v-model="selectedPlayers"
                             :fields="fields"
                             :options="players"
                             @remove="fetchGames"
                             @close="fetchGames"
                             :multiple="true"
                             :close-on-select="false"
                             :clear-on-select="false"
                             :preserve-search="true" placeholder="Pick some" label="name"
                             track-by="id" :preselect-first="true"/>
            </b-col>
        </b-row>

        <b-table striped hover :fields="fields" :fixed="true" :items="scores">
            <template v-slot:cell(status)="row">
                <b-button variant="danger" size="sm"
                          @click="recordModal(row.item, $event.target)"
                          :hidden="row.item.status" class="mr-1">
                    Record
                </b-button>
            </template>
        </b-table>
        <b-pagination
                v-model="pagination.page"
                :total-rows="pagination.totalSize"
                :per-page="pagination.size"
                aria-controls="my-table"
                align="fill"
                @change="fetchGames"
        />
        <!-- Info modal -->
        <b-modal size="lg" :id="recordGameModal.id"
                 @hidden="resetModal"
                 @ok="recordGame"
                 title="Record game">
            <div class="container col-md-12">
                <div class="row">
                    <div class="col-md-4 team1">
                        {{ recordGameModal.content.t1_goalkeeper }}
                    </div>

                    <div class="col-md-4 offset-md-4">
                        {{ recordGameModal.content.t1_strikers }}
                    </div>
                </div>
                <div class="score-container">
                    <b-form-select class="score-select" v-model="recordGameModal.t1_score" :options="scoreOptions"/>
                    :
                    <b-form-select class="score-select" v-model="recordGameModal.t2_score" :options="scoreOptions"/>
                </div>
                <div class="row">
                    <div class="col-md-4 team1">
                        {{ recordGameModal.content.t2_goalkeeper }}
                    </div>

                    <div class="col-md-4 offset-md-4">
                        {{ recordGameModal.content.t2_strikers }}
                    </div>
                </div>
            </div>
            <template v-slot:modal-footer="{ ok, cancel }">
                <b-button size="sm" variant="success" @click="ok()">
                    Save
                </b-button>
                <b-button size="sm" variant="danger" @click="cancel()">
                    Cancel
                </b-button>
            </template>
        </b-modal>
    </b-container>
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
                scoreOptions: [0, 1, 2, 3, 4, 5],
                players: [],
                selectedPlayers: [],
                scores: [],
                show: true,
                pagination: {
                    totalSize: 0,
                    page: 0,
                    size: 10,
                    totalPages: 0,
                },
                selectedGateTypes: false,
                gameTypes: [
                    {text: "Future", value: false},
                    {text: "Played", value: true},
                    {text: "All", value: "all"},
                ],
                fields: [
                    {key: 't1_goalkeeper', sortable: true},
                    {key: 't1_strikers', sortable: true},
                    {key: 't1_score', sortable: true},
                    {key: 'status', sortable: true, label: "Status"},
                    {key: 't2_score', sortable: true},
                    {key: 't2_goalkeeper', sortable: true},
                    {key: 't2_strikers', sortable: true},
                ],
                recordGameModal: {
                    id: 'info-modal',
                    t1_score: 0,
                    t2_score: 0,
                    content: {}
                }
            }
        },
        mounted() {
            if (this.players.length === 0 || this.scores.length === 0) {
                axios.get(process.env.VUE_APP_URL + "/players")
                    .then(response => response.data.map(p => ({id: p.id, name: `${p.firstName} ${p.lastName}`})))
                    .then(response => this.players = response)
                    .then(() => this.fetchGames())
            }
        },
        methods: {
            resetModal() {
                console.log("reset modal")
                this.recordGameModal.t1_score = 0;
                this.recordGameModal.t2_score = 0;
            },
            recordModal(item, button) {
                console.log("Emitting info")
                this.recordGameModal.content = item
                this.$root.$emit('bv::show::modal', this.recordGameModal.id, button)
            },
            playerToName(player) {
                return `${player.firstName} ${player.lastName}`
            },
            recordGame(bvModalEvt) {
                let content = this.recordGameModal.content;

                let request = {
                    idTeam1: content.t1_id,
                    idTeam2: content.t2_id,
                    scoreTeam1: content.t1_score,
                    scoreTeam2: content.t2_score,
                };

                console.log(request);
                bvModalEvt.preventDefault()
                axios.put(process.env.VUE_APP_URL + `/games/${content.id}`, request)
                    .then(() => {
                        this.$root.$emit('bv::hide::modal', this.recordGameModal.id)
                        this.fetchGames({id: ""})
                    })
            },
            mapToGame: function (g) {
                return {
                    id: g.id,
                    t1_id: g.teams[0].id,
                    t1_goalkeeper: this.playerToName(g.scores[0].team.players[0]),
                    t1_strikers: this.playerToName(g.scores[0].team.players[1]),
                    t1_score: Math.max(0, g.scores[0].score),
                    status: g.played,
                    t2_id: g.teams[1].id,
                    t2_score: Math.max(0, g.scores[1].score),
                    t2_goalkeeper: this.playerToName(g.scores[1].team.players[0]),
                    t2_strikers: this.playerToName(g.scores[1].team.players[1])

                }
            },
            fetchGames(removedOption) {
                let request = new URLSearchParams();
                if (this.selectedGateTypes !== "all") {
                    request.append("played", this.selectedGateTypes)
                }
                if (this.selectedPlayers.length === 0 || (this.selectedPlayers.length === 1 && removedOption !== null)) {
                    this.players.forEach(p => request.append("players", p.id))
                } else {
                    this.selectedPlayers
                        //FIX The mulitiselect has a bug that emit remove event before removing element form model
                        .filter(value => value.id !== removedOption.id)
                        .forEach(p => request.append("players", p.id))
                }

                request.append("page", removedOption - 1)
                request.append("size", this.pagination.size)

                axios.get(process.env.VUE_APP_URL + "/games", {params: request})
                    .then(response => {

                        this.pagination.totalPages = response.data.totalPages - 1;
                        this.pagination.totalSize = response.data.totalElements;
                        console.log(response)

                        return response.data.content.map(game => {

                            return this.mapToGame(game)
                        })
                    })
                    .then(response => this.scores = response)
            }
        }
    }
</script>
<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>
<style>
    .team1 {
        text-align: right;
    }

    .score-container {
        z-index: 2;
        position: absolute;
        left: 50%;
        top: 5%;
        margin-left: -5%;
    }

    .score-select {
        width: 30px;
        text-align: center;
        padding: 0;
        background: none;
        -moz-appearance: none;
        -webkit-appearance: none;
        appearance: none;
        text-align-last: center;
    }

    .container-fluid {
        padding: 0;
    }
</style>
