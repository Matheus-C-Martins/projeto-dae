<!-- eslint-disable -->
<template>
  <v-card>
    <v-card-title class="headline"> {{nome}} {{escalao}}: </v-card-title>
    <v-card-text>
      <v-row dense>
        <v-col>
          <v-data-table
            :loading='loadingTreinadores'
            loading-text='A carregar treinadores... Aguarde um momento'
            item-key='username'
            expand-icon
            :headers='headers'
            :items='treinadores'
            class='elevation-1'
            no-data-text='Não existem treinadores'
            :search="searchTreinador">
            <template v-slot:top>
              <v-toolbar flat color='white'>
                <v-toolbar-title> Treinadores </v-toolbar-title>
                <v-divider class='mx-4' inset vertical></v-divider>
                <v-text-field
                  v-model="searchTreinador"
                  label="Procurar por Username ou Email"
                  hide-details
                  outlined
                  dense
                ></v-text-field>
              </v-toolbar>
            </template>
          </v-data-table>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-data-table
            :loading='loadingHorarios'
            loading-text='A carregar horários... Aguarde um momento'
            expand-icon
            :headers='headersHorarios'
            :items='horarios'
            class='elevation-1'
            no-data-text='Não existem horários'
            :search="searchHorario">
            <template v-slot:top>
              <v-toolbar flat color='white'>
                <v-toolbar-title> Horários </v-toolbar-title>
                <v-divider class='mx-4' inset vertical></v-divider>
                <v-text-field
                  v-model="searchHorario"
                  label="Procurar por dia da semana"
                  hide-details
                  outlined
                  dense
                ></v-text-field>
              </v-toolbar>
            </template>
          </v-data-table>
        </v-col>
      </v-row>
    </v-card-text>
    <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color='primary' dark @click='back'> Voltar </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
/* eslint-disable */
export default {
  props: ['nome', 'escalao'],
  data () {
    return {
      loadingTreinadores: true,
      loadingHorarios: true,
      dialogTreinador: false,
      dialogHorario: false,
      searchTreinador: '',
      searchHorario: '',
      headers: [
        { text: 'Username', value: 'username', align: 'center', sortable: false },
        { text: 'Nome', value: 'nome', align: 'center', sortable: false, filterable: false },
        { text: 'Email', value: 'email', align: 'center', sortable: false}
      ],
      headersHorarios: [
        { text: 'Dia da Semana', value: 'diaSemana', align: 'center', sortable: false },
        { text: 'Hora de Início', value: 'horaInicio', align: 'center', sortable: false, filterable: false },
        { text: 'Hora de Fim', value: 'horaFim', align: 'center', sortable: false, filterable: false},
      ],
      treinadores: [],
      horarios: []
    }
  },
  created () {
    this.getTreinadores();
  },
  methods: {
    back () {
      this.$emit("close");
    },
    getTreinadores(){
      this.$axios.$get(`/api/modalidades/${this.nome}&&${this.escalao}`).then(modalidade => {
        this.treinadores = modalidade.treinadores;
        this.horarios = modalidade.horarios;
        this.loadingHorarios = this.loadingTreinadores = false;
      });
    }
  }
}
</script>
