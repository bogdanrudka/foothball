<template>
    <b-modal id="recordingModal" size="lg" v-show="show"
             @hidden="resetModal"
             @ok="recordGame"
             title="Record game">
        <div class="container col-md-12">
            <div class="row">
                <div class="col-md-4 team1">
                    {{ content.t1_goalkeeper }}
                </div>

                <div class="col-md-4 offset-md-4">
                    {{ content.t1_strikers }}
                </div>
            </div>
            <div class="score-container">
                <b-form-select class="score-select" v-model="recordGameModal.t1_score" :options="scoreOptions"/>
                :
                <b-form-select class="score-select" v-model="recordGameModal.t2_score" :options="scoreOptions"/>
            </div>
            <div class="row">
                <div class="col-md-4 team1">
                    {{ content.t2_goalkeeper }}
                </div>

                <div class="col-md-4 offset-md-4">
                    {{ content.t2_strikers }}
                </div>
            </div>
        </div>
    </b-modal>
</template>

<script>
    import axios from "axios";

    export default {
        name: "GameRecorderModal",
        data() {
            return {
                show: false,
                scoreOptions: [0, 1, 2, 3, 4, 5],
                recordGameModal: {
                    id: 'info-modal',
                    t1_score: 0,
                    t2_score: 0,
                    content: {}
                }
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
                axios.put(`/games/${content.id}`, request)
                    .then(() => {
                        this.$root.$emit('bv::hide::modal', this.recordGameModal.id)
                    })
            },
        }
    }
</script>

<style scoped>

</style>