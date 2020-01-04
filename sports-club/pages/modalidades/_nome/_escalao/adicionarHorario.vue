<!-- eslint-disable -->
<template>
  <v-card>
    <v-card-title class="headline"> Adicionar Horário </v-card-title>
    <v-card-text style="padding-bottom: 0px">
      <v-row dense>
        <v-col>
          <v-select
            v-model="diaSemana"
            :items="dias"
            label="Dia da Semanda"
            :error-messages="diaSemanaErrors"
            outlined
            dense
            @input="$v.diaSemana.$touch()"
            @blur="$v.diaSemana.$touch()">
          </v-select>
        </v-col>
        <v-col>
          <v-text-field
            v-model="horaInicio"
            label="Hora de Início"
            :error-messages="horaInicioErrors"
            outlined
            dense
            @input="$v.horaInicio.$touch()"
            @blur="$v.horaInicio.$touch()">
          </v-text-field>
        </v-col>
        <v-col>
          <v-text-field
            v-model="horaFim"
            label="Hora de Fim"
            :error-messages="horaFimErrors"
            outlined
            dense
            @input="$v.horaFim.$touch()"
            @blur="$v.horaFim.$touch()">
          </v-text-field>
        </v-col>
      </v-row>
    </v-card-text>
    <v-card-actions style="padding-top: 0px">
      <v-spacer></v-spacer>
      <v-btn color="blue darken-1" text @click="close"> Cancelar </v-btn>
      <v-btn color="blue darken-1" text @click="adicionar"> Adicionar </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
/* eslint-disable */
import { validationMixin } from 'vuelidate'
import { required, helpers, minLength, maxLength } from 'vuelidate/lib/validators'
export const horaRegex = helpers.regex('horaRegex', /^(?:([01]?\d|2[0-3]):([0-5]?\d))?$/)

export default {
  props: [ 'nome', 'escalao' ],
  mixins: [validationMixin],
  validations: {
    diaSemana: { required },
    horaInicio: { required, horaRegex, minLength: minLength(5), maxLength: maxLength(5) },
    horaFim: { required, horaRegex, minLength: minLength(5), maxLength: maxLength(5) },
  },
  data () {
    return {
      diaSemana: '',
      horaInicio: '',
      horaFim: '',
      dias: [
          "Segunda-feira",
          "Terça-feira",
          "Quarta-feira",
          "Quinta-feira",
          "Sexta-feira",
          "Sábado",
          "Domingo"
      ]
    }
  },
  computed: {
    diaSemanaErrors () {
      const errors = []
      if (!this.$v.diaSemana.$dirty) { return errors }
      !this.$v.diaSemana.required && errors.push('Dia da semana é obrigatório.')
      return errors
    },
    horaInicioErrors () {
      const errors = []
      if (!this.$v.horaInicio.$dirty) { return errors }
      !this.$v.horaInicio.required && errors.push('Hora de início é obrigatória.')
      !this.$v.horaInicio.horaRegex && errors.push('Hora de início tem de estar no formato "HH:MM".')
      !this.$v.horaInicio.minLength && errors.push('Hora de início tem de estar no formato "HH:MM".')
      !this.$v.horaInicio.maxLength && errors.push('Hora de início tem de estar no formato "HH:MM".')
      return errors
    },
    horaFimErrors () {
      const errors = []
      if (!this.$v.horaFim.$dirty) { return errors }
      !this.$v.horaFim.required && errors.push('Hora de fim é obrigatória.')
      !this.$v.horaFim.horaRegex && errors.push('Hora de fim tem de estar no formato "HH:MM".')
      !this.$v.horaFim.minLength && errors.push('Hora de fim tem de estar no formato "HH:MM".')
      !this.$v.horaFim.maxLength && errors.push('Hora de fim tem de estar no formato "HH:MM".')
      return errors
    }
  },
  methods: {
    close() {
      this.$emit("close");
    },
    adicionar() {
      this.$v.$touch()
      if (this.$v.$error) {
        return
      }
      this.$axios.$post(`/api/horarios/${this.diaSemana}&&${this.horaInicio}&&${this.horaFim}/modalidades`, {
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
