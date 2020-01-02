<!-- eslint-disable -->
<template>
  <v-card>
    <v-card-title class="headline"> {{ title }} </v-card-title>
    <v-card-text class="pa-0">
      <v-container style="padding-bottom: 0px; padding-top: 0px;">
        <v-row dense>
          <v-col>
            <v-text-field
              v-model='atleta.username'
              label='Username'
              outlined
              dense
              readonly
              disabled>
            </v-text-field>
          </v-col>
          <v-col>
            <v-text-field
              v-model='atleta.nome'
              label='Nome'
              outlined
              dense>
            </v-text-field>
          </v-col>
        </v-row>
        <v-row dense>
          <v-col>
            <v-text-field
              v-model='atleta.email'
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
        <v-btn color="blue darken-1" text @click="close"> Cancelar </v-btn>
        <v-btn color="blue darken-1" text @click="edit"> Editar </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  /* eslint-disable */
  props: [
    'title',
    'atleta'
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
        this.$axios.$put(`/api/atletas/${this.atleta.username}`, {
          password: this.password,
          nome: this.atleta.nome,
          email: this.atleta.email
        })
          .then(() => {
            this.close()
          })
      } else {
        this.$axios.$put(`/api/atletas/${this.atleta.username}`, {
          password: null,
          nome: this.atleta.nome,
          email: this.atleta.email
        })
          .then(() => {
            this.close()
          })
      }
    }
  }
}
</script>
