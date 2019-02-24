package process;

import process.kernel.EntityEqualityContract;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class ProfileTest implements EntityEqualityContract<UUID, Profile>
{

    @BeforeEach
    void setUp() throws Exception
    {
    }

    @Test
    void identity()
    {
        final UUID identity = UUID.randomUUID();
        assertThat(Profile.with(identity).getIdentity()).isEqualTo(identity);
    }

    @Override
    public Class<Profile> getType()
    {
        return Profile.class;
    }

}
