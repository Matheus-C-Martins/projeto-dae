<!-- eslint-disable -->
<template>
  <v-card>
    <v-card-title class="headline"> Increver Atleta Em Nova Modalidade </v-card-title>
    <v-card-text class="pa-0">
      <v-container style="padding-bottom: 0px; padding-top: 0px;">
        <v-form align="center">
          <v-row dense>
            <v-col>
              <v-card color="#e2e2e2">
                <h6 style=""> Username: </h6> {{atleta.username}}
              </v-card>
            </v-col>
            <v-col>
              <v-card color="#e2e2e2">
                <h6 style=""> Nome: </h6> {{atleta.nome}}
              </v-card>
            </v-col>
          </v-row>
        </v-form>
        <v-row dense>
          <v-col>
            <v-select
            v-model="modalidade"
            :items="modalidades"
            label="Modalidade"
            outlined
            dense
            no-data-text="Não existem modalidades disponíveis para inscrição.">
            <template slot="selection" slot-scope="data">
              {{ data.item.nome }} {{ data.item.escalao }}
            </template>
            <template slot="item" slot-scope="data">
              {{ data.item.nome }} {{ data.item.escalao }}
            </template>
            </v-select>
          </v-col>
        </v-row>
      </v-container>
    </v-card-text> 
    <v-card-actions style="padding-top: 0px">
        <v-spacer></v-spacer>
        <v-btn color="blue darken-1" text @click="close"> Cancelar </v-btn>
        <v-btn color="blue darken-1" text @click="inscrever"> Inscrever </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  /* eslint-disable */
  props: [ 'atleta', 'modsAtleta'],
  data () {
    return {
      valid: true,
      value: true,
      modalidade: {},
      modalidades: []
    }
  },
  created() {
    this.getModalidades();
  },
  methods: {
    close() {
      this.$emit("close");
    },
    inscrever () {
      this.$axios.$post(`/api/atletas/${this.atleta.username}/modalidades`, {
        nome : this.modalidade.nome,
        escalao : this.modalidade.escalao
      })
      .then(() => {
        this.close()
      })
    },
    async getModalidades() {
      var response = await this.$axios.$get('/api/modalidades');
      this.modalidades = response;
      if(this.modalidades.length != this.modsAtleta.length){
        var aux = response;
        for(var i = 0; i < this.modalidades.length; ++i){
          for (var j = 0; j < this.modsAtleta.length; ++j){
            if (this.modalidades[i].nome === this.modsAtleta[j].nome && this.modalidades[i].escalao === this.modsAtleta[j].escalao) {
                aux.splice(i, 1)
            }
          }
        }
        this.modalidades = aux;
      } else {
        this.modalidades = [];
      }
    }
  }
}
</script>
