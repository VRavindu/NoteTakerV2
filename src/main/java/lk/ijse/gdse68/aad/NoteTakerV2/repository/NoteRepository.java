package lk.ijse.gdse68.aad.NoteTakerV2.repository;

import lk.ijse.gdse68.aad.NoteTakerV2.entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, String> {
}
