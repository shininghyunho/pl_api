package chaegang.pl_api.domain.athlete

import org.springframework.data.jpa.repository.JpaRepository

interface AthleteRepository : JpaRepository<Athlete, Long>