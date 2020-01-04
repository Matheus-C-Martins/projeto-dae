<!-- eslint-disable -->
<template>
  <v-card>
    <v-card-title class="headline"> Adicionar Atleta </v-card-title>
    <v-card-text style="padding-bottom: 0px">
      <v-row dense>
        <v-col>
          <v-select
            v-model="atleta"
            :items="atletas"
            label="Atletas"
            :error-messages="atletaErrors"
            outlined
            dense
            @input="$v.atleta.$touch()"
            @blur="$v.atleta.$touch()"
            no-data-text="Não existem atletas disponíveis para inscrição.">
            <template slot="selection" slot-scope="data">
              {{ data.item.username }} - {{ data.item.nome}}
            </template>
            <template slot="item" slot-scope="data">
              {{ data.item.username }} - {{ data.item.nome}}
            </template>
          </v-select>
        </v-col>
      </v-row>
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
  props: [ 'nome', 'escalao' ],
  mixins: [validationMixin],
  validations: { atleta: { required } },
  data () {
    return {
      atleta: {},
      atletas: []
    }
  },
  computed: {
    atletaErrors () {
      const errors = []
      if (!this.$v.atleta.$dirty) { return errors }
      !this.$v.atleta.required && errors.push('Atleta é obrigatório.')
      return errors
    }
  },
  created () {
    this.$axios.$get('/api/atletas').then((atletas) => {
      this.atletas = atletas;
    })
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
      this.$axios.$post(`/api/atletas/${this.atleta.username}/modalidades`, {
        nome : this.nome,
        escalao : this.escalao
      })
      .then(() => {
        this.close()
      })
    },
  }
}
</script>
