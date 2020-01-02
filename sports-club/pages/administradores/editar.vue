<!-- eslint-disable -->
<template>
  <v-card>
    <v-card-title class="headline"> {{ title }} </v-card-title>
    <v-card-text class="pa-0">
      <v-container style="padding-bottom: 0px; padding-top: 0px;">
        <v-row dense>
          <v-col>
            <v-text-field
              v-model='administrador.username'
              label='Username'
              outlined
              dense
              readonly
              disabled>
            </v-text-field>
          </v-col>
          <v-col>
            <v-text-field
              v-model='administrador.nome'
              label='Nome'
              outlined
              dense>
            </v-text-field>
          </v-col>
        </v-row>
        <v-row dense>
          <v-col>
            <v-text-field
              v-model='administrador.email'
              label='Email Adress'
              outlined
              dense>
            </v-text-field>
          </v-col>
          <v-col>
            <v-text-field
              v-model='password'
              label='Password'
              outlined
              dense>
            </v-text-field>
          </v-col>
        </v-row>
      </v-container>
    </v-card-text> 
    <v-card-actions style="padding-top: 0px">
        <v-spacer></v-spacer>
        <v-btn color="blue darken-1" text @click="close">Cancelar</v-btn>
        <v-btn color="blue darken-1" text @click="edit">Editar</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  /* eslint-disable */
  props: [
    'title',
    'administrador'
  ],
  data () {
    return {
      password: ''
    }
  },
  methods: {
    close() {
      this.password = ''
      this.$emit("close");
    },
    edit () {
      if (this.password !== '') {
        //console.log(this.password)
        this.$axios.$put(`/api/administradores/${this.administrador.username}`, {
          password: this.password,
          nome: this.administrador.nome,
          email: this.administrador.email
        })
          .then(() => {
            this.close()
          })
      } else {
        this.$axios.$put(`/api/administradores/${this.administrador.username}`, {
          password: null,
          nome: this.administrador.nome,
          email: this.administrador.email
        })
          .then(() => {
            this.password = ''
            this.close()
          })
      }
    }
  }
}
</script>
