<!-- eslint-disable -->
<template>
  <v-card>
    <v-card-title class="headline"> Nova Modalidade Para Treinar </v-card-title>
    <v-card-text class="pa-0">
      <v-container style="padding-bottom: 0px; padding-top: 0px;">
        <v-form align="center">
          <v-row dense>
            <v-col>
              <v-card color="#e2e2e2">
                <h6 style=""> Username: </h6> {{treinador.username}}
              </v-card>
            </v-col>
            <v-col>
              <v-card color="#e2e2e2">
                <h6 style=""> Nome: </h6> {{treinador.nome}}
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
            @change="getModalidadeEscaloes()"
            :error-messages="modalidadeErrors"
            outlined
            dense
            @input="$v.modalidade.$touch()"
            @blur="$v.modalidade.$touch()"
            no-data-text="Não existem modalidades disponíveis para inscrição.">
            <template slot="selection" slot-scope="data">
              {{ data.item.nome }}
            </template>
            <template slot="item" slot-scope="data">
              {{ data.item.nome }}
            </template>
            </v-select>
          </v-col>
          <v-col>
            <v-select
            v-model="escalao"
            :items="escaloes"
            label="Escalao"
            :error-messages="escalaoErrors"
            outlined
            dense
            @input="$v.escalao.$touch()"
            @blur="$v.escalao.$touch()"
            no-data-text="Escolha uma modalidade primeiro.">
            <template slot="selection" slot-scope="data">
              {{ data.item.escalao }}
            </template>
            <template slot="item" slot-scope="data">
              {{ data.item.escalao }}
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
/* eslint-disable */
import { validationMixin } from 'vuelidate'
import { required } from 'vuelidate/lib/validators'

export default {
  props: [ 'treinador' ],
  mixins: [validationMixin],
  validations: {
    escalao: { required },
    modalidade: { required }
  },
  data () {
    return {
      valid: true,
      value: true,
      modalidade: {},
      modalidades: [],
      escalao: {},
      escaloes: []
    }
  },
  computed: {
    escalaoErrors () {
      const errors = []
      if (!this.$v.escalao.$dirty) { return errors }
      !this.$v.escalao.required && errors.push('Escalão é obrigatório.')
      return errors
    },
    modalidadeErrors () {
      const errors = []
      if (!this.$v.modalidade.$dirty) { return errors }
      !this.$v.modalidade.required && errors.push('Modalidade é obrigatória.')
      return errors
    }
  },
  created() {
    this.getModalidades();
  },
  methods: {
    close() {
      this.$emit("close");
    },
    inscrever() {
      this.$v.$touch()
      if (this.$v.$error) {
        return
      }
      this.$axios.$post(`/api/treinadores/${this.treinador.username}/modalidades`, {
        nome : this.modalidade.nome,
        escalao : this.escalao.escalao
      })
      .then(() => {
        this.close()
      })
    },
    getModalidades() {
      this.$axios.$get('/api/modalidades').then((modalidades) => {
        this.modalidades = modalidades
      });
    },
    getModalidadeEscaloes() {
      this.$axios.$get(`/api/modalidades/${this.modalidade.nome}`).then((escaloes) => {
        this.escaloes = escaloes
      });
    }
  }
}
</script>
