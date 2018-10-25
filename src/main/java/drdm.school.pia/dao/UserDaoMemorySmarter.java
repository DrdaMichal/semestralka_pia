package drdm.school.pia.dao;

import drdm.school.pia.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository("userDaoMemorySmarter")
public class UserDaoMemorySmarter extends GenericDaoMemory<User> implements UserDao, UserDetailsService {

    protected final Map<String, User> usernameIndex;

    public UserDaoMemorySmarter() {
        //webapp is by generally a multi-thread application
        this.usernameIndex = new ConcurrentHashMap<>();
    }

    @Override
    public User save(User value) {
        //note that this implementation is only eventually consistent!
        usernameIndex.put(value.getUsername(), value);
        return super.save(value);
    }

    @Override
    public void remove(User toRemove) {
        //note that this implementation is only eventually consistent!
        usernameIndex.remove(toRemove.getUsername());
        super.remove(toRemove);
    }

    @Override
    public User findByUsername(String username) {
        //complexity depends on hashing algorithm and number of hash table collisions
        //generally better than O(n)
        //of course memory requirements are higher and implementation complexity grows
        //with number of required indexes
        return usernameIndex.get(username);
    }

    /*
        ############## Spring Security UserDetailsService interface ##################
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return findByUsername(s);
    }
}
